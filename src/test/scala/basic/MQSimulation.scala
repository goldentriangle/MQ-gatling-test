package basic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Headers.Names._
import scala.concurrent.duration._
import bootstrap._
import assertions._

class MQSimulation extends Simulation {

	val httpConf = http
		.baseURL("http://localhost:8080/message-queue-rest")
		.acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
		.disableFollowRedirect

	val headers_1 = Map(
		"Keep-Alive" -> "115")

	val headers_3 = Map(
		"Keep-Alive" -> "115",
		"Content-Type" -> "application/json")

	val headers_6 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Keep-Alive" -> "115",
		"X-Requested-With" -> "XMLHttpRequest")

	val scn = scenario("Scenario name").exec(
		http("request_0")
			.post("/kafka/v0/new333")
            .header("Content-Type", "application/json"))
		.pause(6, 7)
        .exec(
		http("request_1")
			.get("/kafka/v0"))
		.pause(100 milliseconds, 200 milliseconds)
		.exec(
			http("request_2")
				.get("/kafka/v0"))
		.pause(6, 7)
        .exec(
			http("request_3")
				.get("/kafka/v0/new1"))
        .exec(
			http("request_4")
				.get("/kafka/v0/new2"))
		.pause(6, 7)
        .exec(
			http("request_5")
				.get("/kafka/v0/new3"))
		.pause(6, 7)
         .exec(
			http("request_6")
				.put("/kestrel/v0/hoge?msg=gatling"))
		.pause(6, 7)

	setUp(scn.inject(ramp(20 users) over (5 seconds)))
		.protocols(httpConf)
		.assertions(
			global.successfulRequests.percent.is(100))
}
