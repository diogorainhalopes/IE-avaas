terraform {
  required_version = ">= 1.0.0, < 2.0.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}
provider "aws" {
  region     = "us-east-1"
  access_key = "ASIAQJMFMUEZCPEWNIYJ"
  secret_key = "bY2PvNuwdRccuUxrKNYoeQc9UwDsEKiJE8QvYf5Q"
  token      = "FwoGZXIvYXdzEAwaDDPxW43cYZkNU6W8gyLWAdJcU7kDWrD3hm9tKjdtArj7YY4dDT91aXlGl8KiHaMAb7PtywV+JurN+SidRowp8gIiRiXTV1NipKMpyV+XiKXsiGvJm3k1LravrBKChW7up7/QOt8fcclUoo5N4/o8wQ+wiD96caZ6RkTurEr3m2gFp93DPARzeMilZyec8XsfXmKHs9mccNGHsf/eUesjAlyx74wKQjrau8m1lh7yQ7S7roII8WIM7qeLsTKoaQkdoSV7lqVG4xsmACJU/VyGy48cbt68w064uwJxgpUYCHId1VoMQhQo37a0pAYyLcjVbxKK8MvvTwPeXRUf3Q9ksGMX8zD9QKSPRuPRAO+O9M7uY9eQMhlNuamOlA=="
}

variable "nBroker" {
  description = "number of brokers"
  type        = number
  default     = 7
}

resource "aws_instance" "avaas" {
  ami                    = "ami-0b5eea76982371e91"
  instance_type          = "t2.small"
  count                  = var.nBroker
  vpc_security_group_ids = [aws_security_group.instance.id]
  key_name               = "s2key"
  user_data = base64encode(templatefile("creation.sh", {
    idBroker     = "${count.index}"
    totalBrokers = var.nBroker
  }))
  user_data_replace_on_change = true
  tags = {
    Name = "terraform-avaas.${count.index}"
  }
}

output "publicdnslist" {
  value = formatlist("%v", aws_instance.avaas.*.public_dns)
}

resource "aws_security_group" "instance" {
  name = var.security_group_name
  ingress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
}

variable "security_group_name" {
  description = "Terraform Security Group"
  type        = string
  default     = "avaas-security-group"
}

#### RDS ####

variable "db_username" {
  description = "The username for the database"
  type        = string
  sensitive   = true
  default     = "admin"
}

variable "db_password" {
  description = "The password for the database"
  type        = string
  sensitive   = true
  default     = "password"
}

variable "db_name" {
  description = "The name to use for the database"
  type        = string
  default     = "initial_db"
}

resource "aws_db_instance" "avaas-database" {
  identifier_prefix      = "database-avaas-quarkus"
  engine                 = "mysql"
  allocated_storage      = 10
  instance_class         = "db.t2.micro"
  skip_final_snapshot    = true
  publicly_accessible    = true
  vpc_security_group_ids = [aws_security_group.rds.id]
  db_name                = var.db_name
  username               = var.db_username
  password               = var.db_password

}

output "address" {
  value       = aws_db_instance.avaas-database.address
  description = "Connect to the database at this endpoint"
}

output "port" {
  value       = aws_db_instance.avaas-database.port
  description = "The port the database is listening on"
}

resource "aws_security_group" "rds" {
  name = var.security_group_name_rds
  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
}

variable "security_group_name_rds" {
  description = "The name of the security group"
  type        = string
  default     = "terraform-rds-instances"
}


