output "dep_public_ip" {
  value       = aws_instance.dependency.public_dns
  description = "The public IP of the web server"
}

output "blocking_public_ip" {
  value       = aws_instance.blocking.public_dns
  description = "The public IP of the web server"
}