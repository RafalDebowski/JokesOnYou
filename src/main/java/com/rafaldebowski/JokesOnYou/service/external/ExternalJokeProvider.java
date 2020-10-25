package com.rafaldebowski.JokesOnYou.service.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rafaldebowski.JokesOnYou.model.Joke;
import com.rafaldebowski.JokesOnYou.model.JokeException;

import java.util.Set;

public interface ExternalJokeProvider {

    Joke getRandomJoke() throws JokeException;

    Joke getRandomJokeFromCategory(final String category) throws JokeException;

    Set<String> getAvailableCategories() throws JokeException;

}
