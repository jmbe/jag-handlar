class Answer {
    static belongsTo = [student:Student]
  
    String question_key
    String answer

    static constraints = {
      question_key(blank:false)
    }
}
