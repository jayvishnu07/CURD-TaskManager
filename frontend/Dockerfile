
# Stage 1: Build the React app
# FROM node:14 as build

# WORKDIR /app
# COPY frontend/package.json frontend/package-lock.json /
# RUN npm install
# COPY . .

# RUN npm run build 

# # Stage 2: Serve the React app using Nginx
# FROM nginx:alpine

# # ./../nginx/ssl/localhost-key.pem

# COPY frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
# COPY --from=build /app/build /usr/share/nginx/html

# COPY frontend/nginx/ssl/localhost.pem /etc/nginx/ssl/localhost.pem 
# COPY frontend/nginx/ssl/localhost-key.pem /etc/nginx/ssl/localhost-key.pem

# EXPOSE 443
# CMD ["nginx", "-g", "daemon off;"]

# # cmd:
# docker run -it nginx-ssl /bin/sh
# docker run -d -p 443:443 nginx-ssl
# docker build -t nginx-ssl -f nginx/Dockerfile .
# docker build -t front:latest .    
# cd usr/share/nginx/html
#  brew services start nginx    


FROM node:14 as build

WORKDIR /app
COPY frontend/package.json frontend/package-lock.json /
RUN npm install
COPY frontend/ .

RUN npm run build 

# Stage 2: Serve the React app using Nginx
FROM nginx:alpine

# ./../nginx/ssl/localhost-key.pem

COPY frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/build /usr/share/nginx/html

COPY frontend/nginx/ssl/localhost.pem /etc/nginx/ssl/localhost.pem 
COPY frontend/nginx/ssl/localhost-key.pem /etc/nginx/ssl/localhost-key.pem

EXPOSE 443
CMD ["nginx", "-g", "daemon off;"]