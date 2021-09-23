import React, {Component, useState} from "react";
import './login.css';

export default ()=>{

    const [login, setLogin] = useState("");
    const [pwd, setPwd] = useState("");


        return (
            <div>
                <header>
                    <span className="title"><b>Livre d'or</b></span>
                </header>

                <main>

                    <div className="login-container">
                        <div ui-view="error"></div>
                        <div>
                            <h2>Page de connexion</h2>
                        </div>
                        <form action="/login" method="post">
                            <div className="login-input-div">

                                <label htmlFor="username">Email</label>
                                <input id="username" name="username" value={login}
                                       onChange={(e)=>setLogin(e.currentTarget.value)}
                                       type="email"/>
                                <label htmlFor="password">Mot de passe</label>
                                <input id="password"
                                       autoComplete="false"
                                       name="password" type="password"
                                       value={pwd}
                                       onChange={(e)=>setPwd(e.currentTarget.value)}
                                />
                            </div>
                            <div className="login-action-div">
                                <button type="submit">Se connecter
                                </button>
                            </div>
                        </form>


                    </div>
                </main>
            </div>
        )

}
