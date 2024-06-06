# DOCUMENTAÇÃO
*Apresentação do Projeto*
Este projeto é um sistema de reservas de hotel desenvolvido em Java, utilizando o JDK 22 e a IDE NetBeans 21. O objetivo principal do sistema é facilitar a gestão de reservas de vagas, cadastro de clientes e verificação de disponibilidade de dias para funcionários do hotel.
1.1 Introdução
 
Propósito do Software:
O software de reservas de hotel visa otimizar a gestão de reservas, simplificando o trabalho dos atendentes e recepcionistas na administração de reservas feitas por telefone ou e-mail. A solução busca proporcionar uma experiência mais eficiente e organizada, tanto para os funcionários quanto para os clientes, eliminando a necessidade de interação direta do cliente com o sistema.
 
Escopo do Software:
 
O software inclui cadastro de clientes, gerenciamento de reservas, calendário de disponibilidade, histórico de visitas, cadastro de funcionários e geração de relatórios. Não inclui interface de usuário para clientes, integração com sistemas de pagamento, gestão de inventário, suporte a múltiplos hotéis, funcionalidades de marketing ou gestão de manutenção de quarto.
 
 
1.2 Contexto
 
Histórico do Projeto:
 
O projeto iniciou em janeiro de 2024, motivado pela necessidade de modernizar e agilizar os processos de reserva em hotéis. Os stakeholders incluem proprietários do hotel, gerentes, funcionários, equipe de TI e clientes, todos interessados em melhorar a eficiência e a experiência de hospedagem.
 
Descrição Geral do Sistema:
 
O sistema é uma aplicação integrada que facilita a gestão de reservas, cadastro de clientes, verificação de disponibilidade de quartos e geração de relatórios. Composto por interface de usuário, serviço de backend, banco de dados, serviços de relatórios e segurança de autenticação, proporciona uma operação eficiente e organizada.
 
```
+----------------------------------------------------+
|                 Interface de Usuário               |
|         	(JavaFX - Desktop App)             	|
+--------------+----------------------+--------------+
           	|                  	|
           	|                  	|
           	|                  	|
+--------------v----------------------+--------------+
|                Servidor de Aplicação               |
|               (Apache Tomcat - Java EE)            |
+--------------+----------------------+--------------+
           	|                  	|
           	|                  	|
           	|                  	|
+--------------v----------------------+--------------+
|                Serviço de Banco de Dados          |
|                      (MySQL)                   	|
+--------------+----------------------+--------------+
           	|                  	|
           	|                  	|
           	|                  	|
+--------------v----------------------+--------------+
|                 Serviço de Relatórios             |
|         	(JasperReports - Java Library)     	|
+----------------------------------------------------+

*Funcionalidades Principais*
Reserva de Vaga: Permite que o funcionário reserve uma vaga para um cliente.
Cadastro de Cliente: Permite que o funcionário cadastre novos clientes no sistema.
Verificação de Cadastro: Permite que o funcionário verifique se um cliente já está cadastrado.
Verificação de Dias Disponíveis: Permite que o funcionário verifique a disponibilidade de dias para reservas.

[link]([(https://docs.google.com/document/d/1NFwMmRpKUBo7zU1bOQlZrHj-1S44G153lcb-1nAw8m8/edit?usp=sharing)])
DOCUMENTAÇÃO DA ENGENHARIA E ARQUITETURA DO SOFTWARE

https://trello.com/b/J2VoN2Dv/hotelaria-prime-booking
TRELLO

