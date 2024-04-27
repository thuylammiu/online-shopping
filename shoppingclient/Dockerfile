# Stage 1: Build the Angular app
FROM node:latest AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build -- --prod

# Stage 2: Create the production image
FROM nginx:latest
COPY --from=build /app/dist/shoppingclient /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]