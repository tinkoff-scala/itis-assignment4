package movies.part1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import org.scalacheck.Gen
import java.time.LocalDate

class MovieOrderingSpec extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {
  override implicit val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 1000, minSize = 50, sizeRange = 20)

  implicit val localDateGen: Gen[LocalDate] = {
    val rangeStart = LocalDate.MIN.toEpochDay
    val currentYear = LocalDate.now().getYear
    val rangeEnd = LocalDate.of(currentYear, 1, 1).toEpochDay
    Gen.choose(rangeStart, rangeEnd).map(i => LocalDate.ofEpochDay(i))
  }

  implicit val movieGen: Gen[Movie] = for {
    name <- Gen.alphaStr
    releaseDate <- localDateGen
    rating <- Gen.choose(0, 100)
  } yield Movie(name, releaseDate, rating)

  private val naturalOrdering: Ordering[Movie] = Ordering.fromLessThan((c1, c2) =>
    (c1.name < c2.name) ||
      (c1.name == c2.name) && (c1.releaseDate isBefore c2.releaseDate) ||
      (c1.name == c2.name) && (c1.releaseDate isEqual c2.releaseDate) && (c1.rating < c2.rating)
  )

  "Ascending sort order on all fields" should "be identical to natural ordering" in {
    val providedOrdering: Ordering[Movie] = MovieOrder.orderingFrom(Order.Ascending, Order.Ascending, Order.Ascending)

    forAll(Gen.listOf(movieGen)) { movies =>
      movies.sorted(providedOrdering) should equal(movies.sorted(naturalOrdering))
    }
  }

  "Descending sort order on all fields" should "be identical to reversed natural ordering" in {
    val providedOrdering: Ordering[Movie] =
      MovieOrder.orderingFrom(Order.Descending, Order.Descending, Order.Descending)

    forAll(Gen.listOf(movieGen)) { movies =>
      movies.sorted(providedOrdering) should equal(movies.sorted(naturalOrdering.reverse))
    }
  }

  "Keeping sort order on all fields" should "not change the order of elements" in {
    val providedOrdering: Ordering[Movie] = MovieOrder.orderingFrom(Order.Identity, Order.Identity, Order.Identity)

    forAll(Gen.listOf(movieGen)) { movies =>
      movies.sorted(providedOrdering) should equal(movies)
    }
  }

  "Ascendingending sort order only by the \"name\" field" should "be the same as natural ordering by that field" in {
    val providedOrdering: Ordering[Movie] = MovieOrder.orderingFrom(Order.Ascending, Order.Identity, Order.Identity)

    val fieldOrdering: Ordering[Movie] = Ordering.by(_.name)

    forAll(Gen.listOf(movieGen)) { movies =>
      movies.sorted(providedOrdering) should equal(movies.sorted(fieldOrdering))
    }
  }

  "Descending sort order only by the \"name\" field" should "be the same as reversed natural ordering by that field" in {
    val providedOrdering: Ordering[Movie] = MovieOrder.orderingFrom(Order.Descending, Order.Identity, Order.Identity)

    val fieldOrdering: Ordering[Movie] = Ordering.by[Movie, String](_.name).reverse

    forAll(Gen.listOf(movieGen)) { movies =>
      movies.sorted(providedOrdering) should equal(movies.sorted(fieldOrdering))
    }
  }
}
