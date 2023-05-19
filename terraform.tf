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
  access_key = "ASIAXQGJ5K22EHWFNLML"
  secret_key = "IzgJMN0iQ916VbjQWUMR0brZC+asA1EAN9sfHl4Y"
  token      = "FwoGZXIvYXdzEGUaDPQPaQXs8iiqPDCEaSLWAacvXqMxyw89UTg1uJtq/jfb/pxVWbjF06Ugvx3hUXTeDCG12g0AdPwgOXISEfdDdGIfR32ENqWTa0nQxETMBMqcDkKMsvEU3mpj8uRhH6pnSzJdww9tJWf7MMGLcbOxjtRh0Ae55p4yghASakEWWEPwK2q+zoetkV4Le72LYBKzCgAeP1nuEeK/vTVGNps+STyt/s0XMidNfJw4O1+7/fnit1MfHMUUp1iGRmdlUiJhCp8j3UiiN1p3FeqXTiPrmW6Zx1vbc2Up9QIaEcrxcRAMYEwBDfQoz5afowYyLcOnTJox7sI4lSrzy0Gh75Nuytw8MhRD/O0b4taJH+1AWKSGW49ZfheS1K9tww=="
}
resource "aws_instance" "AVAAS" {
  ami                         = "ami-0b5eea76982371e91"
  instance_type               = "t2.small"
  vpc_security_group_ids      = [aws_security_group.instance.id]
  key_name                    = "AWSkey"
  user_data                   = file("creation.sh")
  user_data_replace_on_change = true
  tags = {
    Name = "terraform-AVAAS"
  }
}

resource "aws_security_group" "instance" {
  name = var.security_group_name
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 2181
    to_port     = 2181
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 9092
    to_port     = 9092
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "tcp"
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
  description = "terraform security group name"
  type        = string
  default     = "AVAAS-security-group"
}

output "address" {
  value       = aws_instance.AVAAS.public_dns
  description = "Address of the Quarkus EC2 machine"
}

