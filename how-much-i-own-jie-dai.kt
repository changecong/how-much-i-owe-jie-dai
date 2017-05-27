// no need to have a Main class
// multiple class in one file

fun main(arg: Array<String>) {

    // no new
    // support // and /**/
    WordPopulator("How much do I owe JIE DAI ?").print()
 
    // type inferral
    // var to declare variable
    var result = FeeConversation().start("HEY! How much was your meal ?")
    result += FeeConversation().start("How much was the box and other extra fee ?")
    result -= DiscountConversation().start("How much was the discount?")
    
    // val to declare readonly variable
    val payment = result / PeopleConversation().start("How many people ate with you ? Yourself included.").toDouble()
    
    PaymentConversation().start("Now you should pay me $payment")
}


/**
 * Class for populating words one-by-one
 * The constructor is inline with the class declaration 
 */
class WordPopulator(val sentence: String) {

    // fun to declare a function.
    fun print() {
        for (word in sentence.split(" ")) {
            print(word + " ")

            // call java code (Kotlin itself doesn't support static modifier)
            Thread.sleep(200L)
        }
        println()
    } 
}

/**
 * Abstract class of converations.
 */
abstract class Conversation {
    val jie = "Jie"
    val me = "Me"

    // function with return value
    fun start(sentence: String): Int {
        WordPopulator(jie + ": " + sentence).print()
        return handleInput()
    }

    // open keyword indicates the function isn't a final one, overiddable
    open fun handleInput(): Int {
        print(response())

        // !! is for NPE lovers, which indicates the value is nullable and any NPE could be throwable
        // otherwhise there will be a compile error.
        return readLine()!!.toInt()

        // ?. means if (value == null) return null, which skips the following function all, null safe.
        // return readLine()?.toInt()
    }

    // abstract function which is an implicit open one
    abstract fun response(): String
}

/**
 * : to replace extends
 */
class FeeConversation() : Conversation() {
    // explicit override modifier
    // inline method, no returen keyword
    override fun response() = "$me: ￥"     
}

class DiscountConversation() : Conversation() {
    override fun response() = "$me: -￥"
}

class PeopleConversation() : Conversation() {
    override fun response() = "$me: "
}

class PaymentConversation() : Conversation() {
 
    // Overriding method declared as open in the base class
    override fun handleInput(): Int {
        print("\u001bc")
        System.out.flush()  // using Java code directly
        print("$me: [Yes] I'll pay you. [No] Who are you? ")
        var input = readLine()
        // when replaces the switch operator 
        when (input!!.toLowerCase()) {
            "yes" -> printQRCode()
            "no" -> println("$jie: So Sad ...")
            else -> {
                handleInput()
            }
        }

        return 0
    }

    fun printQRCode() = println("QR code")   

    override fun response() = ""
}
