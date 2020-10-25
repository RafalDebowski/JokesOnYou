package com.rafaldebowski.JokesOnYou.service;


import com.rafaldebowski.JokesOnYou.service.JokeDispatcherService;
import com.rafaldebowski.JokesOnYou.service.external.ExternalJokeProvider;
import com.rafaldebowski.JokesOnYou.service.external.Sv443JokeProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class JokeDispatcherServiceTest {

    private List<ExternalJokeProvider> externalJokeProviders = new ArrayList<>();
    private JokeDispatcherService jokeDispatcherService;

    public JokeDispatcherServiceTest() {
        this.externalJokeProviders = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        externalJokeProviders.clear();
    }

    @Test
    void shouldCallAllGetAvailableCategoriesMethodsCallingProvideJokeCategories() {
        //given
        Sv443JokeProvider sv443JokeProvider = Mockito.mock(Sv443JokeProvider.class);
        com.rafaldebowski.JokesOnYou.service.external.ChucknorrisJokeProvider chucknorrisJokeProvider = Mockito.mock(com.rafaldebowski.JokesOnYou.service.external.ChucknorrisJokeProvider.class);

        externalJokeProviders.add(sv443JokeProvider);
        externalJokeProviders.add(chucknorrisJokeProvider);

        jokeDispatcherService = new JokeDispatcherService(externalJokeProviders);

        //when
        jokeDispatcherService.provideJokeCategories();

        //then
        Mockito.verify(sv443JokeProvider, Mockito.times(1)).getAvailableCategories();
        Mockito.verify(chucknorrisJokeProvider, Mockito.times(1)).getAvailableCategories();
    }

    @Test
    void shouldProvideCombinedDistinctElementSet() {
        //given
        Sv443JokeProvider sv443JokeProvider = Mockito.mock(Sv443JokeProvider.class);
        com.rafaldebowski.JokesOnYou.service.external.ChucknorrisJokeProvider chucknorrisJokeProvider = Mockito.mock(com.rafaldebowski.JokesOnYou.service.external.ChucknorrisJokeProvider.class);

        Mockito.when(sv443JokeProvider.getAvailableCategories()).thenReturn(new HashSet<>(Arrays.asList("fashion", "food")));
        Mockito.when(chucknorrisJokeProvider.getAvailableCategories()).thenReturn(new HashSet<>(Arrays.asList("fashion", "political")));

        externalJokeProviders.add(sv443JokeProvider);
        externalJokeProviders.add(chucknorrisJokeProvider);

        jokeDispatcherService = new JokeDispatcherService(externalJokeProviders);

        //when
        final Set<String> set = jokeDispatcherService.provideJokeCategories();
        assertThat(set).size().isEqualTo(3);
        assertThat(set).containsExactlyInAnyOrder("fashion", "food", "political");
    }
}
