# ATVIII-AUTOBOTS-MICROSERVICO-SPRING

Projeto acadêmico da disciplina **Desenvolvimento Web 3** — microsserviço de gestão de uma loja de manutenção veicular e venda de autopeças, construído com **Spring Boot**.

## Sobre o projeto

O `automanager` é uma API REST que evoluiu do simples cadastro de clientes para a base de operação de uma oficina/loja de autopeças. Esta versão (**atualização de base**) adiciona empresas, usuários com perfis, veículos, mercadorias, serviços, vendas e credenciais de acesso, todos com CRUD completo e HATEOAS.

Visão geral dos relacionamentos:

- **Empresa**: unidade comercial que oferece serviços e vende mercadorias. Possui endereço (1:1), telefones (1:N), usuários, mercadorias, serviços e vendas.
- **Usuário**: pessoa associada a uma empresa, com um ou mais **perfis** (`CLIENTE`, `FORNECEDOR`, `FUNCIONARIO`). Possui endereço (1:1), telefones, documentos, e-mails e credenciais (1:N), além de veículos (como proprietário) e mercadorias (como fornecedor).
- **Veículo**: pertence a um usuário (proprietário) e pode estar em vendas.
- **Mercadoria** e **Serviço**: catálogo de uma empresa; a mercadoria pode ter um usuário fornecedor.
- **Venda**: registra empresa, cliente, funcionário, veículo (opcional), mercadorias e serviços.
- **Credencial**: acesso de um usuário ao sistema, por `USUARIO_SENHA` ou `CODIGO_BARRA`.

A API continua implementando os quatro níveis do Modelo de Maturidade de Richardson (RMM):

|Nível|Requisito|Como está no projeto|
|---|---|---|
|0|Uso do protocolo HTTP|API REST trocando JSON sobre HTTP|
|1|URI única por recurso|`/empresas`, `/usuarios`, `/veiculos`, `/mercadorias`, `/servicos`, `/vendas`, `/credenciais`, `/documentos`, `/emails`, `/enderecos` e `/telefones`, cada um com `/{id}`|
|2|Métodos e suas semânticas|GET, POST, PUT e DELETE com status HTTP corretos (ver [Respostas HTTP](#respostas-http-rmm-n%C3%ADvel-2))|
|3|HATEOAS|Respostas com links para o próprio recurso, para a lista e para recursos relacionados (ver [HATEOAS](#hateoas-rmm-n%C3%ADvel-3))|

## Tecnologias

- Java 17
- Spring Boot 2.6.3
- Spring Data JPA / Spring Data JDBC
- Spring HATEOAS
- Banco de dados H2 (em memória, usado em runtime)
- Bean Validation (`javax.validation`)
- Lombok
- Maven (com wrapper `mvnw`)

## Estrutura do projeto

```
atviii-autobots-microservico-spring/automanager
├── src/main/java/com/autobots/automanager
│   ├── controles/        # Controllers REST (um por recurso)
│   ├── Servicos/         # Regras de negócio (e Localizador, que busca entidades ou lança 404)
│   ├── repositorios/     # Interfaces JPA Repository
│   ├── entidades/        # Entidades JPA (Empresa, Usuario, Veiculo, Mercadoria, Servico, Venda, Credencial...)
│   ├── enumeracoes/      # PerfilUsuario, TipoCredencial, TipoVeiculo, TipoDocumento
│   ├── DTO/Request/      # DTOs de entrada (criação/atualização)
│   ├── DTO/Response/     # DTOs de saída (estendem RepresentationModel para carregar links)
│   ├── Mappers/          # Conversão entre entidades e DTOs
│   ├── modelos/          # Adicionadores de links HATEOAS (AdicionadorLink<T> e um por recurso)
│   └── exceptions/       # Tratamento global de erros
└── src/test/             # Testes automatizados
```

## Como executar

Pré-requisitos: JDK 17 e Maven (ou use o wrapper incluso).

```bash
cd atviii-autobots-microservico-spring/automanager
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

Se não conseguir rodar, execute `./mvnw clean` antes e tente novamente.

### Dados iniciais

Ao subir, a aplicação já carrega dados de exemplo: uma empresa, três usuários (um funcionário, um fornecedor e um cliente), um veículo, uma mercadoria, serviços e vendas. O banco H2 fica em memória, então tudo volta a esse estado a cada reinicialização, e os registros criados depois recebem IDs a partir do fim desses dados. Por isso, nos exemplos abaixo, ajuste os IDs conforme o que existir no seu banco.

## Endpoints da API

Todos os recursos seguem o mesmo padrão de CRUD:

|Método|Rota|Descrição|
|---|---|---|
|GET|`/{recurso}`|Lista todos|
|GET|`/{recurso}/{id}`|Busca um por ID|
|POST|`/{recurso}`|Cadastra um novo|
|PUT|`/{recurso}`|Atualiza (o `id` vai no corpo)|
|DELETE|`/{recurso}/{id}`|Remove|

Recursos disponíveis:

|Recurso|Rota|Observações|
|---|---|---|
|Empresa|`/empresas`|Aceita endereço e telefones aninhados no cadastro|
|Usuário|`/usuarios`|Aceita endereço, telefones, documentos e e-mails aninhados; exige ao menos um perfil|
|Veículo|`/veiculos`|Exige `proprietarioId`|
|Mercadoria|`/mercadorias`|Exige `empresaId`; `fornecedorId` é opcional|
|Serviço|`/servicos`|Exige `empresaId`|
|Venda|`/vendas`|Exige `empresaId`, `clienteId` e `funcionarioId`|
|Credencial|`/credenciais`|Exige `usuarioId` e `tipo`|
|Documento|`/documentos`|Exige `usuarioId`|
|E-mail|`/emails`|Exige `usuarioId`|
|Telefone|`/telefones`|Exige `usuarioId` **ou** `empresaId`|
|Endereço|`/enderecos`|Cadastro avulso, ou vinculado a um usuário ou empresa|

### Associação de usuários a empresas

|Método|Rota|Descrição|
|---|---|---|
|POST|`/empresas/{empresaId}/usuarios/{usuarioId}`|Associa um usuário existente à empresa (`204`)|
|DELETE|`/empresas/{empresaId}/usuarios/{usuarioId}`|Remove a associação (`204`; `404` se o usuário não pertence à empresa)|

Também é possível informar `empresaId` ao cadastrar ou atualizar um usuário.

### Regras de negócio

- **Venda**: o cliente precisa ter o perfil `CLIENTE` e o funcionário o perfil `FUNCIONARIO`; o veículo, se informado, precisa pertencer ao cliente; mercadorias e serviços precisam pertencer à empresa da venda. O campo `identificacao` é único (`409` se repetir). A resposta inclui `total`, calculado na hora somando o valor das mercadorias e dos serviços; se um preço mudar depois, o total das vendas existentes muda junto.
- **Mercadoria**: se `fornecedorId` for informado, o usuário precisa ter o perfil `FORNECEDOR` (vale no cadastro e na atualização; `400` caso contrário).
- **Credencial**: `USUARIO_SENHA` exige `nomeUsuario` e `senha`; `CODIGO_BARRA` exige `codigo`. A senha nunca é devolvida nas respostas.
- **Documento**: o `numero` é único (`409` se repetir).
- **Telefone**: pertence a um usuário **ou** a uma empresa, nunca aos dois nem a nenhum (`400`).
- **Exclusão de usuário**: bloqueada com `409` se ele tiver vendas ou veículos vinculados. Mercadorias que ele fornecia continuam existindo, sem fornecedor.
- **Exclusão de veículo**: bloqueada com `409` se houver vendas vinculadas.
- **Exclusão de empresa**: remove também suas vendas, mercadorias, serviços, usuários e os veículos desses usuários.
- **Segurança**: nesta versão não há autenticação, e a senha da credencial é guardada em texto puro. A proteção de acesso e a criptografia ficam para a "atualização de segurança".

## Exemplos de requisição (JSON)

Todas as requisições com corpo usam `Content-Type: application/json`. Datas seguem o formato `yyyy-MM-dd`. Nos **PUT**, o `id` é obrigatório e os demais campos são opcionais: o que for omitido não é alterado.

Valores aceitos nos enums:

|Campo|Valores|
|---|---|
|`perfis`|`CLIENTE`, `FORNECEDOR`, `FUNCIONARIO`|
|`tipo` (veículo)|`HATCH`, `SEDA`, `SUV`, `PICKUP`, `SW`|
|`tipo` (documento)|`CPF`, `CNPJ`, `RG`, `CNH`, `PASSAPORTE`|
|`tipo` (credencial)|`USUARIO_SENHA`, `CODIGO_BARRA`|

### Empresas

`POST /empresas`

```json
{
  "razaoSocial": "Autobots Manutenção Veicular LTDA",
  "nomeFantasia": "Autobots",
  "endereco": {
    "estado": "SP", "cidade": "São José dos Campos", "bairro": "Centro",
    "rua": "Rua das Oficinas", "numero": "100", "codigoPostal": "12200-000"
  },
  "telefones": [{ "ddd": "12", "numero": "999990000" }]
}
```

`PUT /empresas`

```json
{ "id": 1, "razaoSocial": "Autobots LTDA", "nomeFantasia": "Autobots Matriz" }
```

### Usuários

`POST /usuarios`

```json
{
  "nome": "Maria Silva",
  "nomeSocial": "Maria",
  "perfis": ["CLIENTE"],
  "empresaId": 1,
  "endereco": {
    "estado": "SP", "cidade": "Taubaté", "bairro": "Centro",
    "rua": "Rua B", "numero": "20", "codigoPostal": "12000-000"
  },
  "telefones": [{ "ddd": "12", "numero": "988887777" }],
  "documentos": [{ "tipo": "CPF", "numero": "11122233344", "dataEmissao": "2020-01-15" }],
  "emails": [{ "endereco": "maria@email.com" }]
}
```

`PUT /usuarios`

```json
{ "id": 1, "nome": "Maria Souza", "nomeSocial": "Maria", "perfis": ["CLIENTE", "FORNECEDOR"], "empresaId": 1 }
```

### Veículos

`POST /veiculos`

```json
{ "tipo": "SUV", "modelo": "Corolla Cross", "placa": "ABC1D23", "proprietarioId": 1 }
```

`PUT /veiculos`

```json
{ "id": 1, "tipo": "SUV", "modelo": "Corolla Cross Hybrid", "placa": "ABC1D23", "proprietarioId": 1 }
```

### Mercadorias

`POST /mercadorias`

```json
{
  "nome": "Filtro de óleo",
  "descricao": "Filtro de óleo original",
  "validade": "2030-01-01",
  "fabricao": "2025-01-01",
  "quantidade": 10,
  "valor": 49.9,
  "empresaId": 1,
  "fornecedorId": 2
}
```

`PUT /mercadorias`

```json
{ "id": 1, "nome": "Filtro de óleo", "quantidade": 20, "valor": 59.9, "fornecedorId": 2 }
```

### Serviços

`POST /servicos`

```json
{ "nome": "Troca de óleo", "descricao": "Troca completa", "valor": 120.0, "empresaId": 1 }
```

`PUT /servicos`

```json
{ "id": 1, "nome": "Troca de óleo", "descricao": "Troca completa com filtro", "valor": 150.0 }
```

### Vendas

`POST /vendas` (`veiculoId`, `mercadoriaIds` e `servicoIds` são opcionais)

```json
{
  "identificacao": "VENDA-001",
  "empresaId": 1,
  "clienteId": 3,
  "funcionarioId": 1,
  "veiculoId": 1,
  "mercadoriaIds": [1],
  "servicoIds": [1, 2]
}
```

`PUT /vendas` (se `mercadoriaIds` ou `servicoIds` forem enviados, substituem a lista atual)

```json
{
  "id": 1,
  "identificacao": "VENDA-001-B",
  "clienteId": 3,
  "funcionarioId": 1,
  "veiculoId": 1,
  "mercadoriaIds": [1],
  "servicoIds": [1]
}
```

### Credenciais

`POST /credenciais` com usuário e senha

```json
{ "usuarioId": 1, "tipo": "USUARIO_SENHA", "nomeUsuario": "maria", "senha": "123456" }
```

`POST /credenciais` com código de barras

```json
{ "usuarioId": 1, "tipo": "CODIGO_BARRA", "codigo": 123456789 }
```

`PUT /credenciais`

```json
{ "id": 1, "senha": "novaSenha", "inativo": false }
```

### Documentos

`POST /documentos`

```json
{ "tipo": "RG", "numero": "998877", "dataEmissao": "2019-05-10", "usuarioId": 1 }
```

`PUT /documentos`

```json
{ "id": 1, "tipo": "RG", "numero": "998878", "dataEmissao": "2019-05-10" }
```

### E-mails

`POST /emails`

```json
{ "endereco": "joao@email.com", "usuarioId": 1 }
```

`PUT /emails`

```json
{ "id": 1, "endereco": "joao.novo@email.com" }
```

### Telefones

`POST /telefones` de um usuário

```json
{ "ddd": "12", "numero": "977776666", "usuarioId": 1 }
```

`POST /telefones` de uma empresa

```json
{ "ddd": "12", "numero": "966665555", "empresaId": 1 }
```

`PUT /telefones`

```json
{ "id": 1, "ddd": "12", "numero": "911112222" }
```

### Endereços

`POST /enderecos` (avulso; com `usuarioId` ou `empresaId`, vira o endereço daquele dono, nunca os dois)

```json
{
  "estado": "RJ", "cidade": "Rio de Janeiro", "bairro": "Centro",
  "rua": "Rua C", "numero": "5", "codigoPostal": "20000-000",
  "informacoesAdicionais": "Sala 2",
  "usuarioId": 1
}
```

`PUT /enderecos`

```json
{ "id": 1, "cidade": "Niterói", "informacoesAdicionais": "Sala 3" }
```

## Respostas HTTP (RMM nível 2)

Todos os controllers devolvem `ResponseEntity` com o status adequado a cada operação:

|Operação|Sucesso|Falha|
|---|---|---|
|GET (lista)|`200 OK` com a lista|n/a|
|GET (por id)|`200 OK` com o recurso|`404 Not Found` se o id não existe|
|POST|`201 Created`, sem corpo, com o header `Location` apontando para o novo recurso|`400 Bad Request` se os dados são inválidos; `404 Not Found` se uma entidade referenciada (empresa, usuário, veículo etc.) não existe; `409 Conflict` se há valor duplicado|
|PUT|`204 No Content`|`404 Not Found` se o id não existe; `400 Bad Request` se os dados são inválidos|
|DELETE|`204 No Content`|`404 Not Found` se o id não existe; `409 Conflict` se há vínculos que impedem a exclusão|

O corpo de erro tem os campos `titulo` e `message`. Erros de validação (`400`) devolvem um objeto com o nome de cada campo inválido e a mensagem correspondente.

Exemplos de erro:

`POST /usuarios` com corpo `{}` → `400`

```json
{
  "perfis": "Informe ao menos um perfil (CLIENTE, FORNECEDOR ou FUNCIONARIO)",
  "nome": "Nome precisa ter valor"
}
```

`POST /vendas` com um cliente sem o perfil `CLIENTE` → `400`

```json
{
  "titulo": "Erro ao salvar Venda",
  "message": "O usuario informado como cliente não possui o perfil CLIENTE"
}
```

`DELETE /usuarios/1` com vendas vinculadas → `409`

```json
{
  "titulo": "Erro ao deletar Usuario",
  "message": "Usuario possui vendas vinculadas"
}
```

## HATEOAS (RMM nível 3)

As respostas de GET trazem links para navegar entre os recursos. Os links são montados com Spring HATEOAS, por um adicionador em `modelos/` para cada recurso (`AdicionadorLinkEmpresa`, `AdicionadorLinkUsuario`, `AdicionadorLinkVeiculo` etc.), todos implementando a interface `AdicionadorLink<T>`.

|Recurso|Links no item (`GET /{recurso}/{id}`)|Links na lista (`GET /{recurso}`)|
|---|---|---|
|Empresa|`self`, `empresas`, `endereco`, `usuarios`, `mercadorias`, `servicos`, `vendas`; cada telefone traz o seu `self`|`self` em cada empresa|
|Usuário|`self`, `usuarios`, `empresa`, `endereco`, `mercadorias`, `veiculos`, `vendas`; cada telefone, documento, e-mail e credencial traz o seu `self`|`self` em cada usuário|
|Veículo|`self`, `veiculos`, `proprietario`, `vendas`|`self` em cada veículo|
|Mercadoria|`self`, `mercadorias`, `empresa`, `fornecedor`|`self` em cada mercadoria|
|Serviço|`self`, `servicos`, `empresa`|`self` em cada serviço|
|Venda|`self`, `vendas`, `empresa`, `cliente`, `funcionario`, `veiculo`, `mercadorias`, `servicos`|`self` em cada venda|
|Credencial|`self`, `credenciais`, `usuario`|`self` em cada credencial|
|Documento|`self`, `documentos`, `usuario`|`self` em cada documento|
|E-mail|`self`, `emails`, `usuario`|`self` em cada e-mail|
|Telefone|`self`, `telefones`, `usuario` ou `empresa` (dono do telefone)|`self` em cada telefone|
|Endereço|`self`, `enderecos`|`self` em cada endereço|

Exemplo, `GET /vendas/1`:

```json
{
  "id": 1,
  "cadastro": "2026-10-25T21:40:40.528+00:00",
  "identificacao": "1234698745",
  "empresa": 1,
  "cliente": 3,
  "funcionario": 1,
  "veiculo": 1,
  "mercadorias": [1],
  "servicos": [1, 2],
  "total": 400.0,
  "_links": {
    "self": { "href": "http://localhost:8080/vendas/1" },
    "vendas": { "href": "http://localhost:8080/vendas" },
    "empresa": { "href": "http://localhost:8080/empresas/1" },
    "cliente": { "href": "http://localhost:8080/usuarios/3" },
    "funcionario": { "href": "http://localhost:8080/usuarios/1" },
    "veiculo": { "href": "http://localhost:8080/veiculos/1" },
    "mercadorias": { "href": "http://localhost:8080/mercadorias/1" },
    "servicos": [
      { "href": "http://localhost:8080/servicos/1" },
      { "href": "http://localhost:8080/servicos/2" }
    ]
  }
}
```

Exemplo, `GET /veiculos/1`:

```json
{
  "id": 1,
  "tipo": "SUV",
  "modelo": "corolla-cross",
  "placa": "ABC-0000",
  "proprietario": 3,
  "vendas": [1, 2],
  "_links": {
    "self": { "href": "http://localhost:8080/veiculos/1" },
    "veiculos": { "href": "http://localhost:8080/veiculos" },
    "proprietario": { "href": "http://localhost:8080/usuarios/3" },
    "vendas": [
      { "href": "http://localhost:8080/vendas/1" },
      { "href": "http://localhost:8080/vendas/2" }
    ]
  }
}
```

O item individual responde em `application/hal+json`, com os links em `_links`. Na listagem, os links de cada item ficam no campo `links`.

## Modelo de dados

- **Empresa**: razão social, nome fantasia, data de cadastro, endereço (1:1), telefones (1:N), usuários, mercadorias, serviços, vendas
- **Usuario**: nome, nome social, perfis (`CLIENTE`, `FORNECEDOR`, `FUNCIONARIO`), empresa, endereço (1:1), telefones, documentos, e-mails, credenciais (1:N), mercadorias fornecidas, vendas, veículos
- **Veiculo**: tipo (`HATCH`, `SEDA`, `SUV`, `PICKUP`, `SW`), modelo, placa, proprietário, vendas
- **Mercadoria**: nome, descrição, quantidade, valor, datas de fabricação, validade e cadastro, empresa, fornecedor
- **Servico**: nome, descrição, valor, empresa
- **Venda**: identificação (única), data de cadastro, empresa, cliente, funcionário, veículo, mercadorias, serviços, total (calculado)
- **Credencial** (abstrata): data de criação, último acesso, inativo, usuário
    - **CredencialUsuarioSenha**: nome de usuário (único), senha
    - **CredencialCodigoBarra**: código (único)
- **Documento**: tipo (`CPF`, `CNPJ`, `RG`, `CNH`, `PASSAPORTE`), data de emissão, número (único), vinculado a um usuário
- **Email**: endereço, vinculado a um usuário
- **Endereco**: estado, cidade, bairro, rua, número, código postal, informações adicionais
- **Telefone**: DDD, número, vinculado a um usuário ou a uma empresa

## Autor

Hugo Perestrelo — Fatec São José dos Campos, DSM.