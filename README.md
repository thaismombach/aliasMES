# Detecção de Alias 
Trabalho da disciplina Manutenção e Evolução de Software para detecção de alias entre os contribuidores de repositórios GitHub.

## Abordagem
Ao analisar commits de repositorios GitHub podemos verificar que algumas contas possuem nome ou email muito semelhantes, e na maioria dos casos pertencem a uma mesma pessoa, isso pode ser definido como alias. Esse tipo de situação acaba trazendo problemas quando é necessário analisar estatísticas de repositórios que estão relacionados a seus contribuidores. 

Neste código aliases foram tratados utilizando as seguintes abordagens: 
1. Usuários cujo os emails são exatamente iguais. 
A cada novo usuário encontrado contém o email, incluindo a parte de domínio, exatamente igual a de um usuário já existente. Neste caso eles são considerados como a mesma pessoa. 
Exemplo: 
    ```
    Usuário 1: 
    Nome: Thaís Mombach 
    Email: thaismombach@exemplo.com

    Usuário 2: 
    Nome: Thaís O. M. 
    Email: thaismombach@exemplo.com
    ```
2. Similaridade entre nome e emails de diferentes usuários. 
A medida que os usuários são processados é criada uma lista de usuários, onde cada um deles contém sua lista de nomes e emails utilizada em diferentes contas encontradas, ou seja, seus alias. Quando um novo usuário é encontrado, mas não existe nenhum outro usuário, já processado, com email exatamente igual ao dele, é feito um outro tipo de verificação. Para cada novo usuário são feitos os seguintes cálculos em relacão aos usuários já identificados:
      1. É calculada a similaridade entre os dois usuários através da comparação do nome e do email, sem o domínio, com a lista de emails, sem o domínio, do usuário já existe. Para isso, ambos o nome e o email do novo usuário são comparados com todos os emails do usuário já existente, e é obtida a maior similaridade dentre essas calculadas. 
      2. É calculada a similaridade entre os dois usuários através da comparação do nome e do email, sem o domínio, com a lista de nomes do usuário já existe. Para isso, ambos o nome e o email do novo usuário são comparados com todos os nomes do usuário já existente, e é obtida a maior similaridade dentre essas calculadas. 
      
      Com base nesses valores obtidos, os dois usuários são considerados a mesma pessoa se atenderem um dos casos abaixo:
      1. Similaridade entre o nome ou email do novo usuário e os emails de um usuário já existente for maior que um threshold, definido como 0.9, e a distância entre o nome ou email do novo usuário e os nomes de um usuário já existente for maior que um threshold, definido como 0.65. 
      2. Similaridade entre o nome ou email do novo usuário e os emails de um usuário já existente for maior que um threshold, definido como 0.65, e a distância entre o nome ou email do novo usuário e os nomes de um usuário já existente for maior que um threshold, definido como 0.9.
      
      ```
      Usuário 1: 
      Nome: Thaís O Mombach 
      Email: thaismombach@exemplo.com

      Usuário 2: 
      Nome: thaismombach 
      Email: thaisom@exemplo.com
      ```

Para obter os dados do GitHub foi utilizada [JGit](https://github.com/eclipse/jgit) para Java. 

Para calcular a similaridade de duas string foi utilizada a distância normalizada de Levenshtein implementada em uma [biblioteca de terceiros](https://github.com/tdebatty/java-string-similarity).

## Uso 
Para utilizar essa ferramenta basta clonar o projeto Maven deste repositório, e executar a classe do projeto App. A entrada é a URL do projeto. 

Exemplo: 
```
https://github.com/nicolasgramlich/AndEngine.git
```
A saída dessa ferramenta é uma lista de usuários identificados apartir de 1 até no número de usuários total de usuários não considerado alias. Para cada usuário é aparesentada uma lista de pares `(email, nome)` que representam as suas contas identificadas como alias. 

Exemplo: 
```
Cloning from https://github.com/nicolasgramlich/AndEngine.git to nicolasgramlich-AndEngine
User 1: (ngramlich@zynga.com,nicolas gramlich) 
User 2: (ebbybeh@hotmail.com,ebbybeh) 
User 3: (skennedy@zynga.com,scott kennedy) 
User 4: (cornel.cocioaba@yahoo.com,korn3l) 
User 5: (git@winniehell.de,winniehell) 
User 6: (baseball116@gmail.com,spencer elliott) 
User 7: (michal.stawinski@sonymobile.com,michal stawinski) (michal.stawinski@sonyericsson.com,michal stawinski) (michal.stawinski@gmail.com,michal stawinski) 
User 8: (nicolasgramlich@gmail.com,nicolas gramlich (laptop)) (nicolasgramlich@gmail.com,nicolas gramlich) (devnull@localhost,nico) (devnull@localhost,nicolasgramlich) (devnull@localhost,theturtleboy) (devnull@localhost,gil) 
User 9: (playernumberthree@gmail.com,player_03) 
User 10: (oleonov@smartru.com,oleonov) 
User 11: (ivan@vershinin.net,ivan vershinin) 
User 12: (fish@flowerpotgames.com,fish) 
User 13: (ebartley@192.168.186.128,ebartley) 
User 14: (levinotik@gmail.com,levi notik) 
User 15: (administrator@192.168.186.128,administrator) 
User 16: (ybrigo@gmail.com,brig) 
User 17: (xyu@zynga.com,xi yu) 
User 18: (erik@eloff.se,erik eloff) 
User 19: (stevosaurus@gmail.com,stevosaurus) 
User 20: (djpep.dj@gmail.com,sergio viudes) 
User 21: (recastrodiaz@gmail.com,recastro) 
User 22: (makers.f@gmail.com,makers_f) 
User 23: (flashmasterdash@gmail.com,david schreiber) 
User 24: (janne.sinivirta@gmail.com,janne sinivirta) 
User 25: (efung@zynga.com,eric fung) 
User 26: (rodrigo.moraes@gmail.com,rodrigo moraes) 
User 27: (jeremy.logan@gmail.com,jeremy logan)   
```


