package Entity_pack;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private boolean available;

    public MenuItem(){
    }

    public MenuItem(int id, String name, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String toString() {
        return "MenuItem{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", available=" + available + '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private double price;
        private boolean available;

        public Builder setId(int id) {
            this.id = id; return this;
        }
        public Builder setName(String name) {
            this.name = name; return this;
        }
        public Builder setPrice(double price) {
            this.price = price; return this;
        }
        public Builder setAvailable(boolean available) {
            this.available = available; return this;
        }

        public MenuItem build() {
            return new MenuItem(id, name, price, available);
        }
    }


}
