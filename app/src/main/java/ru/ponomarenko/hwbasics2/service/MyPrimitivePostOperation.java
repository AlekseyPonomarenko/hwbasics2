package ru.ponomarenko.hwbasics2.service;

/**
 * Аналог Expression. для создания анонимных классов и последующего вызова в постобработчиках
 * Как передавать функции в котлине - нашел, но в java есть затруднения. Поэтому сделал как передачу через анонимный класс.

 */
public interface MyPrimitivePostOperation {
    void start();
}
