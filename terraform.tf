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
  access_key = "ASIAXQGJ5K22BOI6DPXU"
  secret_key = "3oNu10IciD2MG66qKDbm08oKAaLOK3awCn6TEHmq"
  token      = "FwoGZXIvYXdzEHcaDM6tvbWm42/3bSo2TCLWAUo14pFKkVO5fDEfCPbtazBubBNwfqK1nxMrnKXkuKlPsFE9ghODMIN2meuP8jFqesK0ikVwhjpY6+f9bX0V+ycTm5+GKCs8R7Mk/lfAI9zzSNoAwRjcH4Z+ZCT6OOxY1CZRg3qzsksPHJthhgNQn6RceIqocmIDLVbIV2Qqi0afbgF+IFrWfErKK2a1wirpl6ZFKJSEKbulNITCCqDekXyc2S97Gtag61z86BGINlXSNJGLvfSo5/9K2z2F6ic/ehXClvf62JCgOV2ASkE9ZWcQ0dzhqxEoq5OjowYyLcDySmGPxdjtwBjRRngFYesn7LihyM1hy81kAzYoY+q5/9ES2iRFBv6SkLeJWQ=="
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

