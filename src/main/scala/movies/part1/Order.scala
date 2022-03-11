package movies.part1

sealed trait Order
object Order {
    case object Identity extends Order
    case object Ascending extends Order
    case object Descending extends Order

    implicit class Ops(val order: Order) extends AnyVal {
        def apply[A](ordering: Ordering[A]): Ordering[A] = ???
    }
}
