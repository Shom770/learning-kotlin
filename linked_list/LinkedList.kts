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

class LinkedList() {
    var nodes: MutableList<Node> = mutableListOf()

    fun push(value: Any?) {
        var node = when (value) {
            is Node -> value
            else -> Node(value)
        }
        this.nodes.lastOrNull()?.next = node
        this.nodes.add(node)
    }

    fun pop() : Node {
        if (this.nodes.isEmpty()) {
            throw java.lang.IndexOutOfBoundsException("Can't pop from empty linked list.")
        }

        if (this.nodes.size > 1) {
            this.nodes[this.nodes.size - 2].next = null
        }

        return this.nodes.removeAt(this.nodes.size - 1)
    }

    override fun toString(): String {
        return this.nodes.toString()
    }

    operator fun plus(other: LinkedList) : LinkedList {
        if (other.nodes.isEmpty()) {
            return this
        }
        else if (this.nodes.isEmpty()) {
            return other
        }

        this.nodes.last().next = other.nodes[0]
        this.nodes += other.nodes.subList(0, other.nodes.size)
        return this
    }

    infix fun linkWith(other: LinkedList) : LinkedList {
        return this.plus(other)
    }
}

var linkedList = LinkedList()
var l2 = LinkedList()
l2.push(value=2)

linkedList.push(value=2)
linkedList.push(value=3)
println(linkedList linkWith l2)

println(linkedList)