# DIO Lab Project - Kotlin

This project was made for response of <span lang="portuguese">*"Lab Project - Abstraindo Formações da DIO Usando Orientação a Objetos com Kotlin"*</span>.

## Challenge Description

The challenge description is:

> ***Aprenda Kotlin Com Exemplos: Desafio de Projeto (Lab)***
> 
>*<span lang="portuguese">Desafio de Projeto criado para avaliação do conteúdo técnico explorado no repositório [aprenda-kotlin-com-exemplos](https://github.com/digitalinnovationone/aprenda-kotlin-com-exemplos). **Nesse contexto, iremos abstrair o seguinte domínio de aplicação:**</span>*
> 
>*<span lang="portuguese">**A [DIO](https://web.dio.me) possui `Formacoes` incríveis que visa oferecer um conjunto de `ConteudosEducacionais` voltados para uma stack tecnológica específica, preparando profissionais de TI para o mercado de trabalho. `Formacoes` possuem algumas características importantes, como `nome`, `nivel` e seus respectivos `conteudosEducacionais`. Além disso, tais experiências educacionais têm um comportamento relevante ao nosso domínio, definido pela capacidade de `matricular` um ou mais `Alunos`.**</span>*
> 
> ```kotlin
> TODO("Crie uma solução em Koltin abstraindo esse domínio. O arquivo [desafio.kt] te ajudará 😉")
> ```

## Solution Description

### Considerations

* Even though the challenge doesn't talk about lookup the DIO platform, it makes sense lookup to minimally understand the domain.
* Even though the problem description doesn't inform about the existence of Content Level, when the real content of formation is analysed (e.g, [Formação Android Developer](https://web.dio.me/track/formacao-android-developer)), it makes clear there are Formation Level and Content Level.
* Even though the challenge doesn't talk about UI, it makes sense to add some UI to interact with the domain rules.  

### Code Structure

The code and the code structure was influenced by [Kotlin Coding Convention](https://kotlinlang.org/docs/coding-conventions.html), [Clean Code](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882) and a little of [Tom Hombergs - Get Your Hands Dirty on Clean Architecture](https://github.com/thombergs/buckpal).

![ClassDiagram.png](src%2Fmain%2Fresources%2Fimages%2FClassDiagram.png)

### Test Coverage

The test coverage focus on critical part, it means, the domain, it means, even though the test coverage is important im all parts of the app, the focus of the challenge is the domain.

![CodeCoverage.png](src%2Fmain%2Fresources%2Fimages%2FCodeCoverage.png)

### The Solution UI

The UI is simple, made using only console interaction and [Ansi Escape Codes](https://en.wikipedia.org/wiki/ANSI_escape_code).

To run the application, just use [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) Run:
```
./gradlew run
```

The application starts with the Main Menu:

![MainMenu.png](src%2Fmain%2Fresources%2Fimages%2FMainMenu.png)

Selecting the option `2`, the interaction starts. 

![NewFormation.png](src%2Fmain%2Fresources%2Fimages%2FNewFormation.png)

As a command line app, all necessary data are asked one by one sequentially.

![newFormationNameAndLevel.png](src%2Fmain%2Fresources%2Fimages%2FnewFormationNameAndLevel.png)

Thinking in user experience, there's no *exit key* (e.g., `q` ) in inputs. Just leave blank to return to prior point. 

## Requirements Coverage

Lets revisit the problem description:

> *<span lang="portuguese">**A [DIO](https://web.dio.me) possui `Formacoes` incríveis que visa oferecer um conjunto de `ConteudosEducacionais` voltados para uma stack tecnológica específica, preparando profissionais de TI para o mercado de trabalho. `Formacoes` possuem algumas características importantes, como `nome`, `nivel` e seus respectivos `conteudosEducacionais`. Além disso, tais experiências educacionais têm um comportamento relevante ao nosso domínio, definido pela capacidade de `matricular` um ou mais `Alunos`.**</span>*

During the creation of a Formation, or after that, it's possible to inform the contents and enroll students.

After inform Contents and Students, the formation appears like this:
 
![Formation.png](src%2Fmain%2Fresources%2Fimages%2FFormation.png)

As observed, all requirements are matched. 