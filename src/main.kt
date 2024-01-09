fun main(args: Array<String>){
    var doctor:Person=Doctor()
    var professor:Person=Professor()
    doWork(doctor)
    doWork(professor)
}

fun doWork(person:Person){
    person.whatIsYourJob()
    when(person){
        is Doctor -> person.fixIt()
        is Professor -> person.teachIt()
    }
}