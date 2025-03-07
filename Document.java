public class Document {
    private int id;
    private String title;
    private String author;
    private String body;

    public Document(int id, String title, String author, String body) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}