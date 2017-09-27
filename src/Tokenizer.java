import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bortnik on 27.09.2017.
 */
class Tokenizer {

    States currentState;

    String srcStr;
    int index = 0;
    String delimeters;

    List<String> tokens;

    Tokenizer(String srcStr, String delimeters) {
        currentState = States.Init;
        this.srcStr = srcStr;
        this.delimeters = delimeters;
        tokens = new ArrayList<>();
    }

    /*
    Состояния конечного автомата:
    Init - начальное, в зависимости от следующего символа (буква, разделитель или EOL) переходим в состояния
        Letter, Delimiter или конечному состоянию.
    Letter - переходим в это состояние, если читаем букву. При переходе в него или нахождении в нем
        накапливаем буфер с текущим токеном. При выходе из этого состояния записываем токен в список.
    Delimeter - переходим, если читаем разделитель. Находимся в нем, пока не прочитаем букву или не кончится строка.

 */

    List getTokens() {
        char c;
        StringBuilder token = new StringBuilder();
        while (true) {
            if (index == srcStr.length()) {
                if(currentState == States.Letter)                       // последнее состояние - Letter, значит,
                    tokens.add(token.toString());                       // сохраняем последний токен
                return tokens;
            }

            c = srcStr.charAt(index++);

            switch (currentState) {
                case Init:
                    if (!delimeters.contains(String.valueOf(c))) {      // прочитанный символ - не разделитель (буква)
                        currentState = States.Letter;
                        token.append(c);                                // начало токена, увеличиваем буфер
                    } else {                                            // прочитанный символ - разделитель
                        currentState = States.Delimeter;
                    }
                    break;

                case Letter:
                    if (!delimeters.contains(String.valueOf(c))) {      // прочитанный символ - не разделитель (буква)
                        token.append(c);                                // продолжение токена, увеличиваем буфер
                    } else {                                            // прочитанный символ - разделитель
                        currentState = States.Delimeter;
                        tokens.add(token.toString());                   // конец токена, сохраняем
                        token.setLength(0);
                    }
                    break;

                case Delimeter:
                    if (!delimeters.contains(String.valueOf(c))) {      // прочитанный символ - не разделитель (буква)
                        currentState = States.Letter;
                        token.append(c);                                // начало токена, увеличиваем буфер
                    }
            }
        }
    }
}

enum States {
    Init, Letter, Delimeter
}
