1. Генерация данных
- генерация полей:

  new Todo

  Integer.valueOf(RandomStringUtils.randomNumeric(3)),
  RandomStringUtils.randomAlphabetic(10)
  new Random().nextBoolean()
  RandomStringUtils.randomAlphabetic(10)
  new Random().nextBoolean()
  RandomStringUtils.randomAlphabetic(10)
  new Random().nextBoolean()
  RandomStringUtils.randomAlphabetic(10)
  new Random().nextBoolean()
  RandomStringUtils.randomAlphabetic(10)
  new Random().nextBoolean()
  RandomStringUtils.randomAlphabetic(10)
  new Random().nextBoolean()

полей в json'e может быть очень много, поэтому 
если integer -> делай так: RandomStringUtils.randomNumeric(3)
если строка -> делай так: RandomStringUtils.randomAlphabetic(10)
если boolean -> делай так:   new Random().nex
tBoolean()

-> т.е. надо генерировать по типу полей: описать best practice chatGPT и попросить
сгенерировать:
мне, пожалуйста генератор, который на основании типа данных генерирует мне соответсвующее
значение.
В Java, чтобы понять какой тип данных, используется рефлексия: см. 38:59



- генерация DTO 

Как готовить тест данные?
1. Рандомизировать по умолчанию 

2. Сверху перезаписывать нужны данные специфичные для теста 

3. Очищение данных

4. Использование принципов и паттернов проектирования

5. Использование soft asserts

6. Логирования и отчетность