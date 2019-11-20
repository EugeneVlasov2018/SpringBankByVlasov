### Банковская Система 
####Автор: Власов Евгений

####Краткое описание:
- Существует 2 вида пользователей со своими функциями(Юзер, Администратор)
- Существует 2 вида счетов (Кредитный, Депозитный)

###### Возможности Администратора:
- Создание нового Юзера (Сюда входит: создание самого Юзера, создание первого депозитного счета, создание первой записи в истории операций данного счета)
- Одобрение или отклонение заявки Юзера на создание кредитного счета (в качестве подсказки в теле запроса так же передается общая сумма депозитов Юзера на срок действия кредитного счета)

###### Возможности Юзера:
- Создание дополнительных депозитных счетов
- Формирование заявки на создание кредитного счета (опираясь на общую сумму депозитов на срок действия кредитного счета)
- Пополнение счетов
- Перевод денег на внутрибанковские счета
- Платежи вне банка
- Просмотр списка всех счетов
Формат вывода: тип счета, общий баланс, процентная ставка, сумма депозита/размер кредитного лимита, срок окончания действия счета.
- Просмотр списка истории платежей по каждому счету (с пагинацией страниц). 
Формат вывода: дата и время проведения операции, сумма операции, актуальный баланс, комментарий.

###### Депозитный счет:
- Фиксированная процентная ставка в 5% годовых (Начисляется раз в сутки по формуле: Баланс = Баланс+((Депозит/100)*5))
- Срок действия - 12 месяцев
- Особенности: при создании счета первый взнос кладется на депозит (до конца действия счета именно на эту сумму начисляются проценты и ее нельзя снять). Для осуществления платежей с данного счета необходимо еще раз его пополнить

###### Кредитный счет:
- Фиксированная процентная ставка в 10% годовых (Начисляется раз в сутки по формуле: Баланс = Баланс+(Депозит/10)).
- Срок действия - 6 месяцев.
-Особенности: Проценты начисляются только в случае, если общий баланс уйдет в минус. Начисление процентов останавливается при пополнении счета в плюс.

####В приложении реализовано:
- Авторизация, аутентификация (шифрование пароля с "солью")
- Пул соединений (настроен пул Tomсat)
- Данные хранятся в MySql (при работе с БД используется транзакционность)
- front-end чать сделана при использовании JSP, JSTL, содержит пагинацию, кастомный тег, страницы интернационализированы по 2м языкам
- Так же присутствует сервис, работающий независимо от основной части программы. Цель, - "просыпаться" раз в сутки и начислять проценты по всем счетам
- Часть кода документирована
- Код залоггирован
- Код покрыт тестами
- используемые паттерны: MVC, Singleton, Builder
- кастомные исключения.

####Инструкции по установке:
-После скачивания проекта убедитесь, что у вас существует датабаза MySql c необходимой структурой таблиц. Если ее нет, в папке /recources/ найдите файл createtables.sql со всеми необходимыми коммандами как для создания таблиц, так и для внесения первых данныхБ необходимых для работы.
-Там же находится настроечный файл log4j.properties для настройки логгера (пропишите свой путь для создания лог-файлов)
-Для настройки пула соединений измените context.xml, находящийся в META-INF

##приятной работы
