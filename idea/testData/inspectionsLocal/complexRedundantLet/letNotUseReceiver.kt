// WITH_RUNTIME
// PROBLEM: none

fun foo() {
    val foo: String? = null
    foo?.let {
        "".to("")<caret>
    }
}