db.CreateUser(
    {
        user : "root",
        pwd : "root",
        roles : [
            {
                role : "readWrite",
                db : "PatientNote"
            }
        ]
    }
)