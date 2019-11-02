# T2_OrgArq2

a) Sobre a caracterização do programa

    Como funciona o arquivo de entrada? 

    Como funciona os endereços?


class MakeCaracProgramaEnderecos
class Processador ( vai executar os enderecos)
class HierarquiaMEM ( vai ter tds 'hardwares' de armazenamento)
class MemCache 
class MemCacheL1 & MemCacheL2 & MemCacheL3
class MemRam
class HD

algorit de substituicao enderecos esta na class hierarquia


1
2
3
...
50
40
41
42
43
44
45
46
47
48
...
100


//Ler a caracterizacao do programa conforme pdf
    //  Gerar arquivo de saida com os enderecos conforme o 
    //arquivo lido da caracterizacao
    /*
    * ep:100  //Define endereco final do programa
    * ji:50:40 //Salta incond do endereco 50 para 40
    * bi:41:51:10// Salda cond(10% prob) do ender. 41 p/ 51
    * 0
    1
    2
    3
    4
    ...
    ...
    49
    50
    40
    41
    42
    43
    44
    45
    (calc prob de 10% para prox linha ser 51 ou entao 46)
    (caso true)
    51 
    52
    53
    ...
    ...
    100
    (fim)
    (caso false)
    46
    47
    ...
    ...
    100
    (fim)
    */