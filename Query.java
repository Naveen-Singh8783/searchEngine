public class Query {
    private int id;
    private String text;

    public Query(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Query{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}