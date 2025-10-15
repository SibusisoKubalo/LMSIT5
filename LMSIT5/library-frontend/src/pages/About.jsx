import React from "react";

export default function About() {
  return (
    <div className="about-page">
      <h1>About Library Management System</h1>
      <section className="about-section">
        <h2 className="about-heading">Vision Statement</h2>
        <p className="about-text">
          To redefine the library experience through smart technology that makes
          knowledge accessible, engaging, and effortlessly managed.
        </p>
      </section>
      <section className="about-section">
        <h2 className="about-heading">Mission Statement</h2>
        <p className="about-text">
          Our mission is to build an intelligent, cloud-powered library
          management app that automates daily operations, connects users to
          resources in real time, and empowers libraries to thrive in the digital
          age.
        </p>
      </section>
    </div>
  );
}
