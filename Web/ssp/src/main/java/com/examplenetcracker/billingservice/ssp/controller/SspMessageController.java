package com.examplenetcracker.billingservice.ssp.controller;

import com.examplenetcracker.billingservice.ssp.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class SspMessageController {
    private int counter = 4;//счётчик для добавление новых сообщений

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id","1"); put("text","First message"); }});
        add(new HashMap<String, String>() {{ put("id","2"); put("text","Second message"); }});
        add(new HashMap<String, String>() {{ put("id","3"); put("text","Third message"); }});
    }};
//    @GetMapping
//    public String list() {
//        return  "Привет мир, я SSP!";
//    }
    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);//если id не соответствует выводим 404
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(messages -> messages.get("id").equals(id))//отфильтруем все ненужные значения
                .findFirst()//берём первое попавшееся
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping //добавление нового обьекта
    public  Map<String, String> create(@RequestBody Map<String, String> message){ //получаем сообщение от пользователя
        message.put("id", String.valueOf(counter++)); // добавляем его в новый id с увеличение текущего счетчика
        messages.add(message);//ложим его в список message

        return message; //возвращаем обновлённый message
    }

    @PutMapping("{id}")//обновление текущий записи
    public  Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id); //маленькая База данных

        messageFromDb.putAll(message);//обновим поля которые мы получили от пользователя
        messageFromDb.put("id", id);//тот id по которому был произведён запрос
        return messageFromDb;
    }
    @DeleteMapping("{id}")//удалиение (если успешно удалится придёт 200, ошибка при удалении 500+)
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);//если записи небыло найдено. ошибка 404
        messages.remove(message);//из колекции message удаляем эту запись
    }
}
