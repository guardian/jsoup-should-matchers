package com.gu.scalatest

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class JsoupShouldMatchersTest extends FunSuite with ShouldMatchers with JsoupShouldMatchers {

  test("withValue"){
    "<div><p>Hello, world!</p></div>".asBodyFragment should include element withName("p").and(withValue("Hello, world!"))
    "<div><p>Hello, world!</p></div>".asBodyFragment should not include element (withName("p").and(withValue("Hello, Chris!")))
  }

  test("withName") {
    "<div><p>Hello, world!</p></div>".asBodyFragment should include element withName("p")
    "<div><p>Hello, world!</p></div>".asBodyFragment should not include element (withName("img"))
  }

  test("withClass") {
    "<div><p class='foo'>Hello, world!</p></div>".asBodyFragment should include element withClass("foo")
    "<div><p class='foo bar'>Hello, world!</p></div>".asBodyFragment should include element withClass("bar")
    "<div><p class='foo'>Hello, world!</p></div>".asBodyFragment should not include element (withClass("bar"))
  }

  test("withAttr") {
    "<div><img src='foo.jpg' /></div>".asBodyFragment should include element withAttr("src")
    "<div><img /></div>".asBodyFragment should not include element (withAttr("src"))
  }

  test("withAttrValue") {
    "<div><p id='foo'></p></div>".asBodyFragment should include element withAttrValue("id", "foo")
    "<div><p id='bar'></p></div>".asBodyFragment should not include element (withAttrValue("id", "foo"))
  }

  test("withAttrValueMatching") {
    "<div><img src='foo.jpg' /></div>".asBodyFragment should include element withAttrValueMatching("src", """.+\.jpg""")
    "<div><img src='foo.jpg' /></div>".asBodyFragment should not include element (withAttrValueMatching("src", """.+\.png"""))
  }

  test("selectors should compose (and)") {
    "<div><img class='foo' src='foo.jpg' /></div>".asBodyFragment should
      include element withClass("foo").withName("img")

    "<div><img class='foo' src='foo.jpg' /></div>".asBodyFragment should
      not include element (withName("img").withClass("bar"))
  }

  test("selectors should compose (or)") {
    "<div><img class='foo' src='foo.jpg' /></div>".asBodyFragment should
      include element withClass("foo").withName("img").or(withClass("bar").withName("p"))

    "<div><p class='bar'>Hello, world!</p></div>".asBodyFragment should
      include element withClass("foo").withName("img").or(withClass("bar").withName("p"))

    "<div></div>".asBodyFragment should
      not include element (withClass("foo").withName("img").or(withClass("bar").withName("p")))
  }

  test("selectors should have a readable toString") {
    withName("foo").withClass("bar").toString should equal ("""with name "foo" with class "bar"""")
    withClass("foo").or(withClass("bar")).toString should equal("""with class "foo" or (with class "bar")""")
  }

  test("syntax should support boolean logic operators") {
    (withName("foo") && withClass("bar") || withName("baz") && withClass("qux")).toString should
      equal ("""with name "foo" with class "bar" or (with name "baz" with class "qux")""")
  }

  test("img word should match an img tag") {
    "<body><img src='foo.jpg' /></body>".asBodyFragment should include img ()
    "<body><img class='foo' /></body>".asBodyFragment should include img withClass("foo")

    "<body><p>Hello, world!</p></body>".asBodyFragment should not include img ()
    "<body><img class='foo' /></body>".asBodyFragment should not include img (withClass("bar"))
  }
  
  test("video word should match a video tag") {
    "<body><video src='foo.mp4' /></body>".asBodyFragment should include video ()
    "<body><video class='foo' /></body>".asBodyFragment should include video withClass("foo")

    "<body><p>Hello, world!</p></body>".asBodyFragment should not include video ()
    "<body><video class='foo' /></body>".asBodyFragment should not include video (withClass("bar"))
  }

  test("table th should include class") {
    "<body><table><thead><th class='bold left'>heading 1</th></thead></table></body>".asBodyFragment should include th (withClass("bold") and withClass("left"))
  }


  test("table tr should include class") {
    "<body><table><tbody><td class='bold right'>data 1</td></tbody></table></body>".asBodyFragment should include td (withClass("bold") and withClass("right"))
  }
}
