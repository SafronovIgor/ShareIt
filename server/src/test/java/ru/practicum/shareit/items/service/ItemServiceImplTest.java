package ru.practicum.shareit.items.service;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.items.Item;
import ru.practicum.shareit.items.repository.ItemRepository;
import ru.practicum.shareit.users.User;
import ru.practicum.shareit.users.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ItemServiceImpl.class)
@ActiveProfiles("test")
class ItemServiceImplTest {
    @Autowired
    ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createItem() {
        var randomItem = createRandomItem();
        itemRepository.save(randomItem);
        assertDoesNotThrow(() -> {
            itemRepository.findById(randomItem.getId()).orElseThrow(
                    () -> new NoSuchElementException("Item with id " + randomItem.getId() + " not found"));
        });
    }

    @Test
    void getAllItemByIdOwner() {
        var count = new Random().nextInt(5);
        var user = userRepository.save(createRandomUser());
        for (int i = 0; i < count; i++) {
            itemRepository.save(createRandomItem(user));
        }
        assertNotNull(user);
        assertEquals(count, itemService.getAllItemByIdOwner(user.getId()).size());
    }

    private Item createRandomItem() {
        return Item.builder()
                .name(RandomString.make(50))
                .description(RandomString.make(50))
                .build();
    }

    private Item createRandomItem(User ownerItem) {
        return Item.builder()
                .name(RandomString.make(50))
                .description(RandomString.make(50))
                .owner(ownerItem)
                .build();
    }

    private User createRandomUser() {
        return User.builder()
                .name(RandomString.make(10))
                .email(RandomString.make(5) + "@ya.ru")
                .build();
    }
}