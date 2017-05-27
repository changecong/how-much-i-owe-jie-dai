fun main(arg: Array<String>) {

    // 1. no new
    // 2. implicit type

    WordPopulator("How much do I owe JIE DAI ?").print()

    var result = FeeConversation().start("HEY! How much was your meal ?")
    result += FeeConversation().start("How much was the box and other extra fee ?")
    result -= DiscountConversation().start("How much was the discount?")
    var payment = result / PeopleConversation().start("How many people ate with you ? Yourself included.").toDouble()
    
    PaymentConversation().start("Now you should pay me $payment")
}


/**
 * 
 */
class WordPopulator(val sentence: String) {
    fun print() {
        for (word in sentence.split(" ")) {
            print(word + " ")
            Thread.sleep(200L)
        }
        println()
    } 
}

abstract class Conversation {
    val jie = "Jie"
    val me = "Me"

    fun start(sentence: String): Int {
        WordPopulator(jie + ": " + sentence).print()
        return handleInput()
    }

    open fun handleInput(): Int {
        print(response())
        return readLine()!!.toInt()
    }

    abstract fun response(): String
}

class FeeConversation() : Conversation() {
    override fun response() = "$me: ￥"     
}

class DiscountConversation() : Conversation() {
    override fun response() = "$me: -￥"
}

class PeopleConversation() : Conversation() {
    override fun response() = "$me: "
}

class PaymentConversation() : Conversation() {
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
