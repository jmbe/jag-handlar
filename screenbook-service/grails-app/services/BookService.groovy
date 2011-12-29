import se.jaghandlar.Book;

class BookService {

    static transactional = true

    def findOrCreate(String name) {
      def book = Book.findByName(name);
      if (book == null) {
        log.info("Creating new book ${name}")
        book = new Book(name:name)
        book.save()
      }

      return book

    }
}
