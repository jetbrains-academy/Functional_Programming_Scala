object Task:
  class Box(val content: Set[Any])
  class Key()

//  def lookForKey(box: Box): Option[Key] =
//    var contents = box.content
//    while contents.nonEmpty do
//      val item = contents.head
//      item match
//        case key: Key => return Some(key)
//        case box: Box =>
//          contents = contents - box ++ box.content
//    None

  def lookForKey(box: Box): Option[Key] =
    def go(item: Any): Option[Key] =
      item match
        case key: Key => Some(key)
        case box: Box =>
          box.content.flatMap(go).headOption
    go(box)

//  @main
//  def main() =
//    val box = Box(Set(Box(Set(Box(Set.empty), Box(Set.empty))), Box(Set(Key(), Box(Set.empty)))))
//    println(lookForKey(box))

  enum Tree:
    case Node(val payload: Int, val left: Tree, val right: Tree)
    case Leaf(val payload: Int)

  import Tree.*

  def sumTree(tree: Tree): Int =
    tree match
      case Leaf(payload) => payload
      case Node(payload, left, right) =>
        payload + sumTree(left) + sumTree(right)


  @main
  def main() =
    /**
     *     4
     *    / \
     *   2  5
     *  / \
     * 1  3
     */
    val tree = Node(4, Node(2, Leaf(1), Leaf(3)), Leaf(5))
    println(sumTree(tree))


//
//  function look_for_key (main_box) {
//    let pile = main_box.make_a_pile_to_look_through();
//    while (pile is not empty) {
//      box = pile.grab_a_box();
//      for (item in box) {
//        if (item.is_a_box()) {
//          pile.append(item)
//        } else if (item.is_a_key()) {
//          console.log("found the key!")
//        }
//      }
//    }
//  }