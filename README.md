# Desafio Java

Desafio técnico sobre Java com Spring boot

# No que consiste o desafio?

Criar uma API REST para gestão de usuários e carros, implementando um controle de acesso. Além disso é necessário implementar o controle de acesso, que é baseado no token JWT com Spring security.

# Ferramentas utilizadas (algumas são obrigatórias, outras desejáveis):

1. Spring boot 3
2. Java 17 (requisito mínimo Java 8)
3. Banco de dados H2
4. Flyway - Para migração de dados
5. Testes unitários usando MockMvc
6. Senha do usuário com criptografia - bcrypt
7. Heroko - Para deploy da aplicação

# Testes

1. Para executar a aplicação, é necessário clonar o projeto e realizar a importação na IDE que desejar. Para isso, siga os seguinte passos:

	* [Clone o projeto](https://github.com/rcbrasileiro/desafiojava.git)
	* Atualize as dependências do maven
	* Execute a aplicação
	* Obs: A aplicação sobre na porta 8080, caso deseje substituir, edite o arquivo **application.properties**
	
2. Para executar os testes unitários, pode-se fazer das seguintes formas:
	* Ir até a raiz do projeto e executar o comando **mvn test** (para isso é necessário ter a versão do maven configurada nas variáveis do ambiente.
	* Criar uma tarefa do tipo Maven build e no goal colocar **test**
	* Clicar com o botão direito do mouse em cima do diretório de teste (**src/main/test**) e depois **Run as > Junit Test** ou simplesmente o atalho ** ALT + SHIFT + X, T**

# ESTÓRIAS DE USUÁRIO

##1. Controle de acesso:

	* SU01 - Autenticação do usuário:
		* Como um usuário, quero poder me autenticar no sistema usando minhas credenciais (login/senha) para acessar os recursos que devem ser protegidos.
		* Critérios de Aceitação:
			- Implementar a autenticação usando Spring Security e JWT.
			- Validar as credenciais do usuário ao fazer login.
			- Gerar um token JWT válido após a autenticação bem-sucedida.
			- Retornar as informações do usuário e o token JWT para o cliente após a autenticação.
			
	* SU02 - Expiração e Invalidação do Token JWT:
		* Como um usuário autenticado, quero que meu token JWT expire após um período de tempo específico e seja invalidado para garantir a segurança da minha conta.
		* Critérios de Aceitação:
			- Configurar uma expiração para o token JWT.
			- Implementar um mecanismo para invalidar o token JWT após a expiração.
			- Gerar um token JWT válido após a autenticação bem-sucedida.
			- Requerer que os usuários façam login novamente após a expiração do token JWT.
			
	* SU03 - Acesso a recursos públicos e protegídos:
		* Como um usuário do sistema, gostaria de acessar algumas partes do sistema sem a necessidade de autenticação, para que eu possa visualizar informações públicas ou acessar funcionalidades básicas sem a necessidade de login.
		* Critérios de Aceitação:
			- Como usuário não autenticado, posso acessar os recursos públicos disponíveis.
			- Os endpoints públicos não exigirão autenticação, então não é necessário enviar o token na requisição.
			
	* SU04 - Acesso as minhas informações:
		* Como um usuário do sistema, gostaria de acessar informações detalhadas sobre mim.
		* Critérios de Aceitação:
			- Usuário validado e autenticado.
			- Retornar as informações do usuário.
			- Retornar a data de criação do usuário e a últivez que o usuário realizou o login.
			
##2. Gestão dos usuários:

	* SU05 - Listagem dos usuários:
		* Como um usuário não autenticado, quero listar todos os usuários cadastrados no sistema.
		* Critérios de Aceitação:
			- Retornar a listagem dos usuários.
			
	* SU06 - Cadastrar novo usuário
		* Como um usuário não autenticado, quero cadastrar um novo usuário no sistema.
		* Critérios de Aceitação:
			- Usuário criado no sistema.
			- Informar que o usuário foi criado com sucesso.
			- E-mail já existente: retornar um erro com a mensagem “Email already exists”.
			- Login já existente: retornar um erro com a mensagem “Login already exists”.
			- Campos inválidos: retornar um erro com a mensagem “Invalid fields”.
			- Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.
			
	* SU07 - Buscar usuário por identificador
		* Como um usuário não autenticado, quero buscar um usuário pelo seu identificador.
		* Critérios de Aceitação:
			- Retornar os dados do usuário caso exista.
			
	* SU08 - Atualizar usuário
		* Como um usuário não autenticado, quero atualizar um usuário no sistema.
		* Critérios de Aceitação:
			- Usuário atualizado no sistema.
			- Informar que o usuário foi atualizado com sucesso.
			- E-mail já existente: retornar um erro com a mensagem “Email already exists”.
			- Login já existente: retornar um erro com a mensagem “Login already exists”.
			- Campos inválidos: retornar um erro com a mensagem “Invalid fields”.
			- Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.
			
	* SU08 - Remover usuário
		* Como um usuário não autenticado, quero remover um usuário no sistema.
		* Critérios de Aceitação:
			- Usuário removido no sistema.			
			
##3. Gestão dos carros:

	* SU09 - Listagem dos carros:
		* Como um usuário autenticado, quero listar todos os carros do usuário autenticado no sistema.
		* Critérios de Aceitação:
			- Retornar a listagem dos carros.
			
	* SU10 - Cadastrar novo carro
		* Como um usuário autenticado, quero cadastrar um novo carro no sistema.
		* Critérios de Aceitação:
			- Carro criado no sistema.
			- Informar que o carro foi criado com sucesso.
			- Token não enviado: retornar um erro com a mensagem “Unauthorized”.
			- Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”.
			- Placa já existente: retornar um erro com a mensagem “License plate already exists”.
			- Campos inválidos: retornar um erro com a mensagem “Invalid fields”.
			- Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.
		
	* SU11 - Buscar carro por identificador
		* Como um usuário autenticado, quero buscar um carro pelo seu identificador.
		* Critérios de Aceitação:
			- Retornar os dados do carro caso exista.
			- Token não enviado: retornar um erro com a mensagem “Unauthorized”.
			- Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”.
			
	* SU12 - Remover carro
		* Como um usuário autenticado, quero remover um carro no sistema.
		* Critérios de Aceitação:
			- Usuário removido no sistema.
			- Token não enviado: retornar um erro com a mensagem “Unauthorized”.
			- Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”.
			
	* SU13 - Atualizar carro
		* Como um usuário aotenticado, quero atualizar um carro no sistema.
		* Critérios de Aceitação:
			- Carro atualizado no sistema.
			- Informar que o carro foi atualizado com sucesso.
			- Token não enviado: retornar um erro com a mensagem “Unauthorized”.
			- Token expirado: retornar um erro com a mensagem “Unauthorized - invalid session”.
			- Placa já existente: retornar um erro com a mensagem “License plate already exists”.
			- Campos inválidos: retornar um erro com a mensagem “Invalid fields”.
			- Campos não preenchidos: retornar um erro com a mensagem “Missing fields”.

# SOLUÇÃO

* **Spring boot e Java:**

	Justificativa:
	
	O Spring Boot com Java oferece uma solução produtiva e eficiente para o desenvolvimento de aplicativos. Sua popularidade e robustez são reconhecidas na comunidade de desenvolvimento de software. Com configurações inteligentes e predefinidas, o Spring Boot acelera o processo de desenvolvimento, permitindo que os desenvolvedores se concentrem na lógica de negócios do aplicativo. Além disso, sua biblioteca e ecossistema oferecem suporte abrangente para uma ampla gama de requisitos de aplicativos, desde acesso a banco de dados até segurança e desenvolvimento de microserviços. A facilidade de configuração e implantação do Spring Boot também é uma vantagem significativa, permitindo que os aplicativos sejam facilmente implantados e dimensionados conforme necessário, sem a necessidade de servidores de aplicativos externos. Com padrões de projeto sólidos e melhores práticas incorporadas, o Spring Boot promove o desenvolvimento de aplicativos limpos, modulares e de fácil manutenção.
	
	Defesa Técnica:
	
	O Spring Boot com Java é uma escolha técnica robusta para o desenvolvimento de aplicativos empresariais devido à sua maturidade, confiabilidade e suporte abrangente. Como parte do ecossistema Spring, o Spring Boot é amplamente adotado e comprovado em uma variedade de cenários de produção. Sua comunidade ativa de desenvolvedores e vasta documentação garantem suporte contínuo e resolução rápida de problemas. Além disso, o Spring Boot oferece flexibilidade e escalabilidade, permitindo que os aplicativos cresçam conforme necessário e atendam às demandas de uma base de usuários em expansão. Com recursos integrados de segurança, desempenho otimizado e interoperabilidade com uma ampla variedade de tecnologias, o Spring Boot proporciona uma base sólida para o desenvolvimento de aplicativos empresariais robustos, seguros e escaláveis.

* **Banco de dados H2 e Flyway:**

  	Justificativa:

	O H2 é um banco de dados leve e versátil, ideal para desenvolvimento e teste de aplicativos. Sua capacidade de ser executado em memória facilita a configuração e elimina a necessidade de instalação de um servidor de banco de dados separado. O Flyway é uma ferramenta de migração de esquema que automatiza e controla a evolução do banco de dados, garantindo que as alterações sejam versionadas e aplicadas de forma consistente em todos os ambientes.

  	Defesa Técnica:
	
	O uso combinado do H2 e do Flyway oferece uma solução eficiente e confiável para o desenvolvimento, teste e implantação de aplicativos. O H2 proporciona uma experiência de desenvolvimento suave, permitindo rápida iteração e depuração de código. O Flyway automatiza a migração do esquema do banco de dados, promovendo a integridade e consistência dos dados em diferentes ambientes e reduzindo o risco de falhas durante a implantação. Juntos, eles facilitam o desenvolvimento de software de alta qualidade e a manutenção eficaz do banco de dados.

* **Heroku:**

  	Justificativa:
	
	O Heroku é uma plataforma de nuvem que oferece uma série de benefícios significativos para o processo de implantação de aplicativos. Ele simplifica drasticamente o processo de implantação, oferecendo integração contínua e implantação contínua (CI/CD), escalabilidade automática, provisionamento e gerenciamento de recursos de infraestrutura. Além disso, o Heroku fornece uma variedade de add-ons e serviços prontos para uso, como bancos de dados, cache e ferramentas de monitoramento, que podem ser facilmente integrados ao aplicativo.

	Defesa Técnica:
	
	O uso do Heroku para o deploy da aplicação oferece diversas vantagens técnicas. Primeiramente, sua integração contínua e implantação contínua simplificam e automatizam o processo de deploy, permitindo atualizações rápidas e frequentes do aplicativo. Além disso, a escalabilidade automática do Heroku garante que o aplicativo possa lidar com variações de carga de maneira eficiente, expandindo automaticamente o número de instâncias conforme necessário. O provisionamento e gerenciamento de infraestrutura também são simplificados, permitindo que os desenvolvedores se concentrem no desenvolvimento do aplicativo em vez de preocupações com a infraestrutura. Por fim, a ampla gama de add-ons e serviços oferecidos pelo Heroku simplifica a integração de funcionalidades adicionais, como bancos de dados e ferramentas de monitoramento, facilitando a manutenção e o monitoramento do aplicativo. Essas vantagens combinadas tornam o Heroku uma escolha atraente e eficaz para o deploy de aplicativos.

* **Jenkis (CI/CD):**

  	Justificativa:

	O Jenkins é uma ferramenta amplamente utilizada para automação de integração contínua e implantação contínua (CI/CD). Ele simplifica o processo de desenvolvimento de software, permitindo a construção, teste e implantação automatizados do código em diferentes ambientes. O Jenkins é altamente flexível e extensível, com suporte a uma variedade de plugins que permitem integração com várias ferramentas e serviços, tornando-o uma escolha versátil para equipes de desenvolvimento.

	Defesa Técnica:
	
	O Jenkins oferece diversas vantagens técnicas significativas. Sua automação de CI/CD simplifica e acelera o processo de desenvolvimento, permitindo atualizações frequentes e confiáveis do software. Ele suporta pipelines de entrega contínua, o que permite a execução automatizada de testes, análise estática de código e implantação em ambientes de teste e produção de forma rápida e consistente. Além disso, sua arquitetura distribuída e capacidade de escalabilidade horizontal garantem desempenho e confiabilidade mesmo em ambientes de desenvolvimento de grande escala. Com uma vasta biblioteca de plugins e uma comunidade ativa, o Jenkins oferece flexibilidade e personalização para atender às necessidades específicas de cada equipe de desenvolvimento. Essas vantagens combinadas tornam o Jenkins uma escolha poderosa para automatizar o ciclo de vida de desenvolvimento de software.

* **Testes unitários:**

	Justificativa:
	
	Os testes unitários são uma prática fundamental no desenvolvimento de software, pois garantem que cada unidade de código funcione conforme o esperado de forma isolada. O uso do MockMvc em testes unitários oferece uma maneira eficaz de simular o comportamento de endpoints da API em um ambiente controlado e previsível. Isso permite que os desenvolvedores testem a lógica de negócios e a integração entre os componentes da aplicação de forma independente, sem depender de serviços externos ou infraestrutura adicional.

	Defesa Técnica:
	
	O MockMvc é uma ferramenta poderosa para testar controladores em aplicações Spring Boot, oferecendo uma API intuitiva para construir e executar testes de integração. Ele permite simular requisições HTTP e validar as respostas retornadas pelos endpoints da API, garantindo o correto funcionamento da lógica de negócios e a integração com outros componentes do sistema. Além disso, o MockMvc oferece recursos avançados, como verificação de status HTTP, validação de conteúdo JSON e manipulação de cabeçalhos de requisição e resposta, tornando-o uma escolha robusta para testes de integração em aplicações Spring Boot. Ao usar o MockMvc em conjunto com bibliotecas de teste como JUnit e Mockito, os desenvolvedores podem criar testes unitários abrangentes e confiáveis que garantem a qualidade e estabilidade da aplicação.
	
* **Spring security e JWT token:**

	Justificativa:
	
	O Spring Security é uma estrutura robusta e amplamente adotada para proteger aplicações web em Java. Ele fornece uma variedade de recursos de segurança, como autenticação, autorização, controle de acesso e prevenção contra ataques comuns, garantindo a integridade e a confidencialidade dos dados da aplicação. Além disso, o uso de tokens JWT (JSON Web Tokens) oferece uma maneira eficiente e escalável de implementar a autenticação baseada em tokens, permitindo que os usuários se autentiquem e acessem recursos protegidos de forma segura e sem estado.

	Defesa Técnica	
	O uso do Spring Security em conjunto com tokens JWT é uma abordagem altamente segura e eficaz para proteger aplicações web modernas. O Spring Security oferece uma configuração flexível e abrangente para lidar com requisitos complexos de segurança, incluindo autenticação com diferentes provedores (como banco de dados, LDAP, OAuth, etc.) e autorização baseada em regras de negócio específicas. Por outro lado, os tokens JWT são uma solução leve e sem estado para autenticação de usuários, permitindo que as aplicações validem a identidade dos usuários de forma eficiente e confiável sem a necessidade de consultar o estado do servidor. Juntos, o Spring Security e os tokens JWT oferecem uma solução completa e segura para proteger aplicações web contra ameaças de segurança, garantindo o acesso seguro aos recursos da aplicação apenas para usuários autenticados e autorizados.
	
	* **Criptografia com BCrypt:**

	Justificativa:
	
	O uso de senhas criptografadas com BCrypt é uma prática recomendada para garantir a segurança das informações sensíveis dos usuários em sistemas web. O BCrypt é um algoritmo de hash de senhas amplamente reconhecido por sua robustez e resistência a ataques de força bruta. Ele utiliza técnicas de salting e stretching para tornar as senhas armazenadas irreconhecíveis mesmo em caso de vazamento do banco de dados. Isso garante a confidencialidade das senhas dos usuários, protegendo contra acesso não autorizado e comprometimento de contas.

	Defesa Técnica	
	
   O uso do BCrypt para criptografar senhas é uma prática essencial de segurança em aplicações web, pois oferece uma camada adicional de proteção contra ataques de força bruta e violações de dados. Ao utilizar o BCrypt, as senhas dos usuários são transformadas em hashes irreversíveis antes de serem armazenadas no banco de dados, garantindo que mesmo em caso de acesso não autorizado ao banco de dados, as senhas originais permaneçam protegidas. Além disso, o BCrypt utiliza um processo de stretching, que aumenta significativamente o tempo necessário para gerar cada hash, dificultando ainda mais tentativas de quebra de senha. Dessa forma, o uso do BCrypt para criptografar senhas é uma escolha segura e eficaz para proteger as informações dos usuários e manter a integridade e confidencialidade dos dados da aplicação.
