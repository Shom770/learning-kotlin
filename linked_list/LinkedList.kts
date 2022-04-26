class Node(value: Any?) {
    val value = value
    var next: Node? = null

    override fun toString(): String {
        val displayNext = if (this.next != null) {
            "Node(value=${this.next?.value})"
        } else {
            "null"
        }
        return "Node(value=${this.value}, next=$displayNext)"
    }
}

class LinkedList {
    var nodes: MutableList<Node> = mutableListOf()

    fun push(value: Any?) {
        var node = when (value) {
            is Node -> value
            else -> Node(value)
        }
        this.nodes.lastOrNull()?.next = node
        this.nodes.add(node)
    }
}

var linkedList = LinkedList()

linkedList.push(value=2)
linkedList.push(value=3)

println(linkedList.nodes)