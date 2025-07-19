ARG  FROMIMAGE_BUILD=registry.dai-ichi-life.com.vn/base-images/node-npm-build:16.20.2-alpine
FROM ${FROMIMAGE_BUILD} AS build

WORKDIR /app
COPY package.json ./package.json
# RUN yarn install
RUN npm cache clean --force && npm install --force

COPY . .
RUN npm run build-ci
