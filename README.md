# Detecçao de Alias 
Trabalho da disciplina Manutenção e Evolução de Software para detecção de alias entre os contribuidores de repositórios GitHub.

## Abordagem
Ao analisar commits de repositorios GitHub podemos verificar que algumas contas possuem nome ou email muito semelhantes, e na maioria dos casos pertencem a uma mesma pessoa. Esse tipo de situação acaba trazendo problemas quando é necessário analisar estatísticas de repositórios que estão relacionados a seus contribuidores. 

Uma forma fácil identificar alias é verificar se duas pessoas tem emails iguais, mas nessa abordagem nem todos os alias são identificados. Neste código foram considerados alias os seguintes casos: 

1. Usuários cujo os emails são iguais. 
2. Usuário cujo o email(desconsiderando a parte de domínio) e nome são similares aos emails de outro usuário, identificado previamente, em pelo menos 80% de acordo com a média das distância de Jaro-Winkler calculadas. 
3. Usuário cujo o email(desconsiderando a parte de domínio) e nome são similares aos nomes, identificados previamente, de outro usuário em pelo menos 80% de acordo com a média das distância de Jaro-Winkler calculadas.

Para obter os dados do GitHub foi utilizada [JGit](https://github.com/eclipse/jgit) para Java. 

Para calcular a semelhança de duas string foi utilizada a distância de Jaro-Winkler implementada em uma [biblioteca de terceiros](https://github.com/tdebatty/java-string-similarity#jaro-winkler).


