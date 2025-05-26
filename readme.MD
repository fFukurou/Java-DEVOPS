# Parte de Java

## Instruções de Execução

1. Clonar o projeto.
2. Abrir o projeto (de preferência com IntelliJ).
3. Ter o Lombok Plugin instalado e habilitar o processamento de anotações quando ele pedir.
4. Optional: se quiser rodar o projeto no seu próprio banco de dados Oracle, trocar as credenciais em 'application.properties' e rodar o arquivo 'ChallengeSchema.sql'.
5. Rodar o arquivo 'SensoriumApplication' no root do projeto.
6. Abrir 'http://localhost:8080/swagger-ui/index.html/' no browser para ver todas as routes documentadas.

## IMPORTANTE:

Se ao executar o projeto, o IntelliJ cuspir o erro "java: cannot find symbol", vá em File -> Settings (atalho CTRL + ALT + S) -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> Selecione "sensorium" - > Marque a opção "Obtain processors from project classpath" ao invés de "Processor path:"

## Integrantes

- Vicenzo Massao - 554833
- Luiz Heimberg - 556864
- Erick Alves - 556862

## Descrição

Os Três cavaleiros do Apocalipse tem como objetivo auxiliar a empresa Mottu no problema de mapeamento das motos em seus pátios, onde usamos tecnologias IOT e um mapa interativo para mapear a região do pátio, além de poder separar por setores personalizados o próprio pátio.

![alt text](imgs/image.png)

---

# Parte DevOps

## Modo de Execução

- Abrir o terminal
- Clonar o Repositorio - "git clone https://github.com/Os-Tres-Motoqueiros-do-Apocalipse-Verde/Java-Advanced.git"
- Abrir o projeto - "cd Java-Advanced" 
- Logar na Azure - "az login"
- rodar o .sh - "./criar_otmav.sh"

## Integrantes

- Vicenzo Massao - 554833
- Luiz Heimberg - 556864
- Erick Alves - 556862
