import React, { useState, useEffect } from "react";
import UserDataService from "../../../api/users/UserDataService";
import BackgroundHome from "../fragments/background/BackgroundHome";
import styles from "../../../css/Forum.module.css";

const ForumPage = () => {
    const [posts, setPosts] = useState([]);
    const [newPost, setNewPost] = useState("");
    const [displayName, setDisplayName] = useState("");
    const [userAvatar, setUserAvatar] = useState("");

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await UserDataService();
                const user = response.data;
                setDisplayName(user.displayName);
                setUserAvatar(user.avatarFile);
            } catch (error) {
                console.error(error);
            }
        };

        fetchUserData();
    }, []);

    useEffect(() => {
        const storedPosts = localStorage.getItem("forumPosts");
        if (storedPosts) {
            setPosts(JSON.parse(storedPosts));
        }
    }, []);

    useEffect(() => {
        localStorage.setItem("forumPosts", JSON.stringify(posts));
    }, [posts]);

    const addPost = () => {
        const post = {
            content: newPost,
            timestamp: new Date().toISOString(),
            author: displayName,
            avatar: userAvatar,
        };

        setPosts([post, ...posts]);
        setNewPost("");
    };

    return (
        <>
            <main>
                <BackgroundHome />
                <section className={styles.forum_container}>
                    <div>
                        <h1 className={styles.forum_title}>Traveling community</h1>
                        <div className={styles.forum_input_container}>
                            <textarea
                                rows="4"
                                cols="50"
                                className={styles.forum_input}
                                value={newPost}
                                onChange={(e) => setNewPost(e.target.value)}
                            ></textarea>
                            <br />
                            <button className={styles.forum_button} onClick={addPost}>
                                Add Post
                            </button>
                        </div>
                        <div className={styles.forum_input_container}>
                            <h2 className={styles.forum_subtitle}>Recent Posts</h2>
                            {posts.length === 0 && <p>No posts yet.</p>}
                            {posts.map((post, index) => (
                                <div key={index} className={styles.forum_post}>
                                    <div className={styles.forum_post_header}>
                                        {post.avatar && (
                                            <img
                                                src={`data:image/png;base64,${post.avatar}`}
                                                alt="User Avatar"
                                                className={styles.forum_avatar}
                                            />
                                        )}
                                        <p className={styles.forum_author}>{post.author}</p>
                                    </div>
                                    <p className="forum-content">{post.content}</p>
                                    <p className={styles.forum_timestamp}>{post.timestamp}</p>
                                    <hr className={styles.forum_divider} />
                                </div>
                            ))}
                        </div>
                    </div>
                </section>
            </main>
        </>
    );
};

export default ForumPage;