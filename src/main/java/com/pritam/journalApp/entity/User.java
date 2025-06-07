package com.pritam.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
@Data
public class User {

    @Id
     private ObjectId id;

    @Indexed(unique = true)        //
    @NonNull
     private String userName;

    @NonNull
     private String password;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

}

/*journalEntries field will contain a list of journal entries for each user
 * ## one to many mapping
 * @DBref -> put the reference of journalEntries in the list.
 * */
