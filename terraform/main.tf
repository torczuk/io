terraform {
  required_version = ">= 0.12"
}

# ------------------------------------------------------------------------------
# CONFIGURE OUR AWS CONNECTION
# ------------------------------------------------------------------------------

provider "aws" {
  region = "ap-southeast-1"
}

# ---------------------------------------------------------------------------------------------------------------------
# DEPLOY A SINGLE EC2 INSTANCE
# ---------------------------------------------------------------------------------------------------------------------

resource "aws_instance" "dependency" {
  # Ubuntu Server 18.04 LTS
  ami                    = "ami-0bb35a5dad5658286"
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.instance.id]
  key_name = "dev-box"

  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update
              sudo apt install -y openjdk-11-jdk
              EOF

  tags = {
    Name = "dependency"
    Type = "jdk"
  }
}

resource "aws_instance" "blocking" {
  # Ubuntu Server 18.04 LTS
  ami                    = "ami-0bb35a5dad5658286"
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.instance.id]
  key_name = "dev-box"

  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update
              sudo apt install -y openjdk-11-jdk
              EOF

  tags = {
    Name = "blocking"
    Type = "jdk"
  }
}

# ---------------------------------------------------------------------------------------------------------------------
# CREATE THE SECURITY GROUP THAT'S APPLIED TO THE EC2 INSTANCE
# ---------------------------------------------------------------------------------------------------------------------

resource "aws_security_group" "instance" {
  name = "boot-security"

  # Inbound HTTP from anywhere
  ingress {
    from_port   = var.server_port
    to_port     = var.server_port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Inbound SSH from anywhere
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Outbount to anywhere
  egress {
    from_port       = 0
    to_port         = 0
    protocol        = "-1"
    cidr_blocks     = ["0.0.0.0/0"]
  }
}