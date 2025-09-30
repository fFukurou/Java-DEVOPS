# Parte de Java

## Instruções de Execução

1. Clonar o projeto.
2. Abrir o projeto (de preferência com IntelliJ).
3. Ter o Lombok Plugin instalado e habilitar o processamento de anotações quando ele pedir.
4. Optional: se quiser rodar o projeto no seu próprio banco de dados Oracle, trocar as credenciais em 'application.properties' (linhas 5 e 6; o Flywya criará as migrations na sua DB).
5. Rodar o arquivo 'OtmavApplication' no root do projeto.
6. Abrir 'http://localhost:8080' para ser dirigido à Home do projeto.
   6.5 (Optional) : Link Swagger (OpenAPI): http://localhost:8080/swagger-ui/index.html
7. O Flyway rodará as migrations automataicamente, e criará um usuário "teste" normal e um usuário "Admin" com permissões extras.
8. a -- email: teste@gmail.com | senha: teste

   b -- email: admin@gmail.com | senha: admin

## IMPORTANTE:

Se ao executar o projeto, o IntelliJ cuspir o erro "java: cannot find symbol", vá em File -> Settings (atalho CTRL + ALT + S) -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> Selecione "otmav" - > Marque a opção "Obtain processors from project classpath" ao invés de "Processor path:"

## Link do Vídeo:

- https://www.youtube.com/watch?v=YJk8cI60Bwc

## Integrantes

2TDSPM:

- Vicenzo Massao - 554833
- Erick Alves - 556862

2TDSPX:

- Luiz Heimberg - 556864

## Descrição

Os Três cavaleiros do Apocalipse tem como objetivo auxiliar a empresa Mottu no problema de mapeamento das motos em seus pátios, principalmente poder separará-las por setores personalizados dentro do próprio pátio.

---
