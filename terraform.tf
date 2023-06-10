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
  access_key = "ASIAQJMFMUEZN7TLXLLL"
  secret_key = "7xq20FMQq+cACRcSH3c4rtkNGgAI6bfA7QIAprvf"
  token      = "FwoGZXIvYXdzEF0aDO2kM1hJydjKK6G7ZiLWATHXnxzzf4RIXnOzG+S4TGiDK1Ypo+j2e90l2KyCScqTzNiozA8d605ugEzv4VbwAOezD7azKyDgy+RIUBiA96/WYT5u5MZK2yysf32m4lhydRb3k5+pWcnb1crN68nYjQDFBIdlX5doAQdDIT6x5OqaExLi+/wLtYm5SPozTd6R+fEfSqh7S5DrPESRD4g+D9j7t/1kXvip4l5EBPdPAebEezWacVBMZq68Kw5yLsEDvhnqwafAQwjNi4rJS6B1Qnotn5YAPokGzN30QY2DjhZJMY9kytMo5P2NpAYyLYYwldscCi+gTITx4bv1k8OYQoR8Y8DHIOuDa8wRAeZsTIlk0m3ANJx1O0k+OA=="
}
resource "aws_instance" "avaas" {
  ami                         = "ami-0b5eea76982371e91"
  instance_type               = "t2.small"
  vpc_security_group_ids      = [aws_security_group.instance.id]
  key_name                    = "s2key"
  user_data                   = file("creation.sh")
  user_data_replace_on_change = true
  tags = {
    Name = "terraform-avaas"
  }
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

output "address" {
  value       = aws_instance.avaas.public_dns
  description = "Address of the Quarkus EC2 machine"
}

