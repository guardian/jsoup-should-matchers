jsoup-should-matchers
=====================

Provides should-matchers compatible with [ScalaTest](http://www.scalatest.org/), for making assertions about HTML fragments.

As yet, it only supports an `includes` word, so you can assert that a fragment does or doesn't include element(s) with certain attributes.

Example
-------

    class HelloWorldTest extends FunSuite with JsoupShouldMatchers {

      test("withName") {

        "<div><p>Hello, world!</p></div>".asBodyFragment should include element withName("p")

        "<div><p>Hello, world!</p></div>".asBodyFragment should not include element (withName("img"))
      }

    }

More examples in `JsoupShouldMatchersTest`.

