# Camel

### Send get request
curl http://localhost:8080/products/test  
curl http://localhost:8080/camel/camel-products/one  
curl http://localhost:8080/camel/camel-products/two

### Send all requests over proxy
curl http://localhost:80/products/test

### Build and run from docker file
docker build -t camel .  
docker run camel

### Go to rabbit admin page
http://localhost:15672  
guest  
guest

