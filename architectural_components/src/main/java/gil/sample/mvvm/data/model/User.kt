package gil.sample.mvvm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "users")
@JsonClass(generateAdapter = true)
data class User(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @Json(name = "id") // moshi
    @SerializedName("id")
    val id: Int,

    @Json(name = "name")
    @SerializedName("name")
    val name: String,

    @Json(name = "email")
    @SerializedName("email")
    val email: String,

    @Json(name = "gender")
    @SerializedName("gender")
    val gender: String,

    @Json(name = "status")
    @SerializedName("status")
    val status: String
)


