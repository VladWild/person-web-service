1) Сбилдить образ 

> docker build -t person_web_service_image . 


2) Запустить полученный из образа контейнер  

> docker run --name person_web_service_container -p 8080:8080 person_web_service_image  

