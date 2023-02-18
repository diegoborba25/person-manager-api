# person-manager-api

Uma API simples para gerenciar pessoas.

## Como a API funciona?

A API cria, atualiza, deleta e lista pessoas e seus endereços. A relação entre uma pessoa e um endereço funciona como uma relação "N pra N", ou seja, a mesma pessoa pode ter varios endereços ao mesmo tempo que um mesmo endereço pode ser usado por váris pessoas. Visto isso, é bom ressaltar que para ser criado um relacionamento entre pessoa e endereço é necessario primeiro criar a pessoa e o endereço que serão relacionados.

## Observações:
Seriam necessárias regras de negócio para: 
- Verificar se a pessoa possui um endereço principal ao salvar, alterar e deletar.
- Verificar se a pessoa/endereço tem um relacionamento cadastrado antes de ser deletado(a).

Sintáticamente seriam possíveis essas implementações, porém, semânticamente faz mais sentido essas verificações serem implementadas no cliente, ou seja, na aplicação que estiver consumindo a API.

# Endereço (Address)

```POST/person-manager-api/addresses``` - Cria um endereço, requer um body com as informações do endereço:

```json
{
    "streetAddress": "Rua Nêmesis",
    "cep": "89030-110",
    "city": "Raccoon City"
}
```

Respostas Possíveis:
```
201 - Created: A pessoa foi criada corretamente. 
400 - Bad Request: A requisição não pode ser aceita, talvez esteja faltando um parâmetro.
422 - Unprocessable Entity: Se o body tiver campos que não podem ser "parseados". 
500 - Server Errors: Erro da API.
```

<br>

```GET/person-manager-api/addresses``` - Lista todos os endereços.

Respostas Possíveis:
```
200 - OK: Tudo ocorreu corretamente. 
404 - Not Found: O resource da requisição não existe.
```

<br>

```GET/person-manager-api/addresses/{idAddress}``` - Consulta um endereço específico:

Respostas Possíveis:
```
200 - OK: Tudo ocorreu corretamente. 
404 - Not Found: O resource da requisição não existe.
```

<br>

```PUT/person-manager-api/addresses/{id}``` - Edita um endereço, requer um body com as informações que serão alteradas:

```json
{
    "streetAddress": "Rua Tyrant",
    "cep": "89030-000",
    "city": "Raccoon City"
}
```

Respostas Possíveis:
```
200 – OK: Tudo ocorreu corretamente. 
400 - Bad Request: A requisição não pode ser aceita, talvez esteja faltando um parâmetro.
404 - Not Found: O resource da requisição não existe. 
422 - Unprocessable Entity: Se o body tiver campos que não podem ser "parseados". 
500 - Server Errors: Erro da API.
```

<br>

```DEL/person-manager-api/addresses``` - Deleta todos os endereços.

Respostas Possíveis:
```
204 - No Content: deletado com sucesso. 
205 - Reset Content: não foi deletado com sucesso. 
500 - Server Errors: Erro da API.
```

<br>

# Pessoa (Person)

```POST/person-manager-api/persons``` - Cria uma pessoa, requer um body com as informações da pessoa:

```json
{
    "name": "Chris Redfield",
    "birthday": "2020-11-16T04:25:03.312Z"
}
```

Respostas Possíveis:
```
201 - Created: A pessoa foi criada corretamente. 
400 - Bad Request: A requisição não pode ser aceita, talvez esteja faltando um parâmetro.
422 - Unprocessable Entity: Se o body tiver campos que não podem ser "parseados". 
500 - Server Errors: Erro da API.
```

<br>

```GET/person-manager-api/persons``` - Lista todas as pessoas.

Respostas Possíveis:
```
200 - OK: Tudo ocorreu corretamente. 
404 - Not Found: O resource da requisição não existe.
```

<br>

```GET/person-manager-api/persons/{idPerson}``` - Consulta uma pessoa específica.

Respostas Possíveis:
```
200 - OK: Tudo ocorreu corretamente. 
404 - Not Found: O resource da requisição não existe.
```

<br>

```PUT/person-manager-api/persons/{idPerson}``` - Edita uma pessoa, requer um body com as informações que serão alteradas:

```json
{
    "name": "Leon S. Kennedy",
    "birthday": "2020-09-17T04:25:03.312Z"
}
```

Respostas Possíveis:
```
200 – OK: Tudo ocorreu corretamente. 
400 - Bad Request: A requisição não pode ser aceita, talvez esteja faltando um parâmetro.
404 - Not Found: O resource da requisição não existe. 
422 - Unprocessable Entity: Se o body tiver campos que não podem ser "parseados". 
500 - Server Errors: Erro da API.
```

<br>

```DEL/person-manager-api/persons``` - Deleta todas as pessoas.

Respostas Possíveis:
```
204 - No Content: deletado com sucesso. 
205 - Reset Content: não foi deletado com sucesso. 
500 - Server Errors: Erro da API.
```

<br>

```POST/person-manager-api/persons/{idPerson}/addresses/{idAddress}``` - Cria um relacionamento entre pessoa e endereço. Pode ou não ter um body, caso não tenha o endereço vai ser considerado como principal por padrão, e caso tenha ele vai utilizar o valor recebido, por ex:

```json
{
    "main": false
}
```

Respostas Possíveis:
```
201 - Created: A pessoa foi criada corretamente. 
409 - Conflict: Se for tentado criar um relacionamento que já existe.   
422 - Unprocessable Entity: Se o body tiver campos que não podem ser "parseados". 
500 - Server Errors: Erro da API.
```

<br>

```GET/person-manager-api/persons/{idPerson}/addresses``` - Lista os endereços da pessoa.

Respostas Possíveis:
```
200 - OK: Tudo ocorreu corretamente. 
404 - Not Found: O resource da requisição não existe.   
```

<br>

```PUT/person-manager-api/persons/{id}/addresses/{idAddress}``` - Altera um relacionamento entre pessoa e endereço, atualmente apenas altera se o endereço é ou não principal. Requer um body:

```json
{
    "main": true
}
```

Respostas Possíveis:
```
200 – OK: Tudo ocorreu corretamente. 
400 - Bad Request: A requisição não pode ser aceita, talvez esteja faltando um parâmetro.
404 - Not Found: O resource da requisição não existe. 
422 - Unprocessable Entity: Se o body tiver campos que não podem ser "parseados". 
500 - Server Errors: Erro da API.
```

<br>

```DEL/person-manager-api/persons/addresses``` - Deleta todos os relacionamentos entre pessoa e endereço.

Respostas Possíveis:
```
204 - No Content: deletado com sucesso. 
205 - Reset Content: não foi deletado com sucesso. 
500 - Server Errors: Erro da API.
```

# Acessando banco de dados

Primeiramente é necessário executar um "Maven Install" no seu projeto. Para que ele instale e configure as dependências corretamente. Na IDE Eclipse é feito desta forma:

<img src="https://lh3.googleusercontent.com/pw/AMWts8CHw-pQYV-q7VemedA_9mO0_g5QBQAmGKtETWfS2MVRAj-ysC8EtSKpbPmWVMiNAA7NkRQiT8k5nzvMjEoHB-RT9dRJZoe10dGuv2SOwvHET2G7hSRIQWoEp3JwdsVazSsIUypY3KMhmmx7WfXDffc=w780-h424-no?authuser=0">

<br>

Após isso, basta executar a aplicação. Então, o console do banco de dados fica disponível no endereço: [localhost:8080/h2-console](http://localhost:8080/h2-console), como na imagem:

<img src="https://lh3.googleusercontent.com/pw/AMWts8CvjSHDf4Mni28Gcwx_vwAbQZJ8GdmSarAYhTeKBvy4AYJ-lPxJkqyXjKNOu8-HO7zp-OhEDhGOYldJDNHT2wcIteG5PtURNv9P851cq_cQ_IsV5VoD-QvRNyLoqPMXyGaXWfO9L9naoqf31b2FRo0=w478-h383-no">

Caso a "JDBC URL" esteja diferente, basta ajustar para: <code>jdbc:h2:mem:database</code>, e então clicar em "<b>Connect</b>".
