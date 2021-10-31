# Customer Api Application

## Features

1. Permite a criação de novos clientes:

```cmd
curl -X POST http://localhost:8888/post \
   -H 'Content-Type: application/json' \
   -d '{"name":"Test","birty":"2000-01-01"}'
```

2. Permite a atualização de clientes existentes:

```cmd
curl -X PUT http://localhost:8888/put \
   -H 'Content-Type: application/json' \
   -d '{"id": 1, "name":"Test Update", "birty":"2002-02-02"}'
```

3. Permite que seja possível listar os clientes de forma paginada:

```cmd
curl -X GET http://localhost:8888/get/all
```

```cmd
curl -X GET http://localhost:8888/get/all?page=1
```

4. Permite buscas por atributos cadastrais do cliente:

Por ID:

```cmd
curl -X GET http://localhost:8888/get/1
```

Por atributos:

```cmd
curl -X GET http://localhost:8888/get \
   -H 'Content-Type: application/json' \
   -d '{"name":"Test"}'
```

5. Permite remoção de clientes:

```cmd
curl -X DELETE http://localhost:8888/delete/1
```

6. Verifica se o sistema está UP:

```cmd
curl -X GET http://localhost:8888/deploy
```

## Comandos e configuração

### Pré-Requisitos
```cmd
docker --version
Docker version 20.10.9, build c2ea9bc
``` 

##### Rodar teste unitário

```cmd
 ./gradlew test
```

##### Executar com Docker

```cmd
docker build -t customer-api-application .
docker run -p 8888:8888 customer-api-application
```
