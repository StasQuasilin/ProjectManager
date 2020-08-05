package utils.answers;

import constants.Keys;

public class ErrorAnswer extends Answer {
    @Override
    String getStatus() {
        return Keys.ERROR;
    }
}
