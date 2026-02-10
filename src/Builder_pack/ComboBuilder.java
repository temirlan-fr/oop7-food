package Builder_pack;

import Entity_pack.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class ComboBuilder {
    private List<MenuItem> items = new ArrayList<>();

    public ComboBuilder addItem(MenuItem item) {
        items.add(item);
        return this;
    }

    public List<MenuItem> build() {
        return items;
    }
}
