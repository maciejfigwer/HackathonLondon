package com.sparksmart;

import com.ciscospark.Spark;
import com.ciscospark.Webhook;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Piotr on 12/3/2016.
 */
public class SparkSmart {

    private static final String accessToken = "OGNlNzc3OWEtZWZkOC00ZTMwLWIyM2YtYWY2YTc3MDA3YWYwODU4ODc0NWMtOGE0";
    public static Spark sparkInstance;

    public static Spark getSpark() {
        if (sparkInstance == null) {
            sparkInstance = Spark.builder()
                    .baseUrl(URI.create("https://api.ciscospark.com/v1"))
                    .accessToken(accessToken)
                    .build();
        }
        return sparkInstance;
    }



    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        // To obtain a developer access token, visit http://developer.ciscospark.com
        String accessToken = "OGNlNzc3OWEtZWZkOC00ZTMwLWIyM2YtYWY2YTc3MDA3YWYwODU4ODc0NWMtOGE0";

        // Initialize the client
        Spark spark = Spark.builder()
                .baseUrl(URI.create("https://api.ciscospark.com/v1"))
                .accessToken(accessToken)
                .build();

        // List the rooms that I'm in
        spark.teams().iterate().forEachRemaining(team -> {
            System.out.println("team=" + team.getName());
        });

        spark.rooms().iterate().forEachRemaining(room -> {
            System.out.println("room=" + room.getTitle() + ", created " + room.getCreated() + ": " + room.getId());
            /*Message message = new Message();
            message.setRoomId(room.getId());
            message.setText("Hello you idiots. I'm a bot and I'm smarter than every single one of you.");
            spark.messages().post(message);*/
        });

        //registerHook();
        // Create a new room
        /*Room room = new Room();
        room.setTitle("Hello World");
        room = spark.rooms().post(room);*/


        // Add a coworker to the room
        /*Membership membership = new Membership();
        membership.setRoomId(room.getId());
        membership.setPersonEmail("wile_e_coyote@acme.com");
        spark.memberships().post(membership);
*/

        /*// List the members of the room
        spark.memberships()
                .queryParam("roomId", room.getId())
                .iterate()
                .forEachRemaining(member -> {
                    System.out.println(member.getPersonEmail());
                });


        // Post a text message to the room
        Message message = new Message();
        message.setRoomId(room.getId());
        message.setText("Hello World!");
        spark.messages().post(message);


        // Share a file with the room
        message = new Message();
        message.setRoomId(room.getId());
        message.setFiles(URI.create("http://example.com/hello_world.jpg"));
        spark.messages().post(message);


        // Create a new team
        Team team = new Team();
        team.setName("Brand New Team");
        team = spark.teams().post(team);


        // Add a coworker to the team
        TeamMembership teamMembership = new TeamMembership();
        teamMembership.setTeamId(team.getId());
        teamMembership.setPersonEmail("wile_e_coyote@acme.com");
        spark.teamMemberships().post(teamMembership);


        // List the members of the team
        spark.teamMemberships()
                .queryParam("teamId", team.getId())
                .iterate()
                .forEachRemaining(member -> {
                    System.out.println(member.getPersonEmail());
                });*/
        /*Webhook webhook = new Webhook();
        webhook.setTargetUrl(new URI("http://964035d5.ngrok.io/sparksmart"));
        webhook.setResource("messages");
        webhook.setEvent("created");
        webhook.setFilter("roomId=Y2lzY29zcGFyazovL3VzL1JPT00vZTdiMjM1NzAtYjk1YS0xMWU2LWEwNTAtMjMwY2RlZmJiYTBk");
        webhook.setName("SparkSmart translation");

        spark.webhooks().post(webhook);*/
    }

    public static void registerHook() throws URISyntaxException {
        Webhook webhook = new Webhook();
        webhook.setTargetUrl(new URI("http://964035d5.ngrok.io/conversation"));
        webhook.setResource("messages");
        webhook.setEvent("created");
        webhook.setName("SparkSmart conversation");

        getSpark().webhooks().post(webhook);
    }
}
